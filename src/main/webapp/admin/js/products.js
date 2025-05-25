/*--------------------------------------------------------
---------------------------------------------------------

                       Manager Product

---------------------------------------------------------
----------------------------------------------------------*/

//---------------Product Data--------------------//
// Lấy danh sách sản phẩm từ server
function fetchProducts() {
    // 13.4.3. products.js gọi AJAX GET đến /api/books/admin
    $.ajax({
        url: '/api/books/admin', type: 'GET', dataType: 'json',
        // 13.4.7.1.5 AJAX callback success, lấy mảng products để hiển thị lên bảng.
        success: function (products) {
            const table = $("#products--table");

            // Xóa DataTables nếu đã được khởi tạo trước đó
            if ($.fn.DataTable.isDataTable(table)) {
                table.DataTable().destroy(); // Hủy DataTables
                table.find("tbody").empty(); // Xóa dữ liệu cũ
            }

            // Thêm tbody mới vào bảng
            const tbody = buildTableProduct(products);
            table.append(tbody);

            // Khởi tạo lại DataTables với phân trang và tìm kiếm
            table.DataTable({
                searching: true, // Kích hoạt tìm kiếm
                info: true, // Hiển thị thông tin tổng số bản ghi
                order: [[0, 'asc']], // Sắp xếp mặc định theo cột đầu tiên (Id)
                language: {
                    url: '/js/i18n/vi.json' // Ngôn ngữ Tiếng Việt
                }, paging: true, // Kích hoạt phân trang
                pageLength: 5, // Số bản ghi mỗi trang
                lengthChange: true, // Kích hoạt thay đổi số lượng bản ghi mỗi trang
            });
        },
        // 13.4.7.2.6 AJAX nhận response lỗi (HTTP 500)
        error: function (xhr, status, error) {
            console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            alert("Không thể lấy danh sách sản phẩm. Vui lòng thử lại sau.");
        }
    });
    console.log("✅ Đã khởi tạo lại DataTables thành công!");
}

function buildTableProduct(products) {
    const tbody = $("<tbody></tbody>");
    products.forEach(book => {
        const row = $("<tr></tr>");
        row.append($(`<td>${book.id}</td>`));
        row.append($(`<td>${book.title}</td>`));
        row.append($(`<td>${book.author}</td>`));
        row.append($(`<td>${book.description}</td>`));
        row.append($(`<td>${book.publishedDate}</td>`));
        row.append($(`<td>${book.stockQty}</td>`));
        row.append($(`<td>${book.price.toLocaleString('vi-VN')} đ</td>`));
        row.append($(`<td><img src="${book.imageUrl}" width="60" height="90"/></td>`));

        // categories là mảng hoặc danh sách -> join thành chuỗi
        const categories = book.categories.map(cat => cat.name).join(", ");
        row.append($(`<td>${categories}</td>`));

        // Cột trạng thái
        const statusText = book.stockQty > 0
            ? '<span class="badge bg-success">Còn hàng</span>'
            : '<span class="badge bg-secondary">Hết hàng</span>';
        row.append($(`<td>${statusText}</td>`));

        row.append($(`<td>
                        <button class="action-btn btn-edit" title="Sửa">
                            <i class="fas fa-pen"></i>
                        </button>
                        <button class="action-btn btn-delete" title="Xóa">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                        </td>`));

        tbody.append(row);
    });
    return tbody;
}

/*--------------------------------------------------------
---------------------------------------------------------

                       Add Product (Thêm sách)

 Mục đích:
 - Hiển thị / ẩn form thêm sách
 - Thu thập dữ liệu từ form
 - Gửi dữ liệu lên server
 - Hiển thị kết quả thêm sách hoặc lỗi

---------------------------------------------------------
----------------------------------------------------------*/

// ===== Form Hiển thị / Ẩn =====
const overlay = document.querySelector(".overlay-addProduct");
const form = document.getElementById("add-product-form");

// 13.1.1.0 Admin chọn “Thêm sách mới”
function showAddProductForm() {
    // 13.1.1.1 Hiển thị form nhập thông tin
    overlay.style.display = "flex"; // Mở form bằng cách hiển thị lớp overlay
}

// Ẩn form và reset dữ liệu
function hideAddProductForm() {
    form.reset();
    overlay.style.display = "none";
}

// 13.1.1.3 adminPage: JS thu thập dữ liệu từ form thành JSON object
function getFormData() {
    const formData = new FormData(form);

    return {
        title: formData.get("title"),
        author: formData.get("author"),
        description: formData.get("description"),
        publishedDate: formData.get("publishedDate"),
        price: parseFloat(formData.get("price")),
        stockQty: parseInt(formData.get("stockQty")),
        imageUrl: formData.get("imageUrl"),
        categoryIds: Array.from(
            form.querySelector("#categoryIds").selectedOptions,
            opt => parseInt(opt.value)
        )
    };
}

// 13.1.1.4 Gửi HTTP POST yêu cầu thêm sách
async function createBook(event) {
    event.preventDefault();
    const data = getFormData();

    try {
        const response = await fetch("/api/books", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            // Xử lý lỗi trả về từ server:
            // 13.1.2.7 adminPage hiển thị thông báo lỗi “VALIDATION_ERROR” từ server.
            // 13.1.2.13 adminPage hiển thị thông báo lỗi “BOOK_DUPLICATE” từ server.
            // 13.1.3.2 adminPage hiển thị thông báo lỗi chung "INTERNAL_ERROR" từ server.
            const errorMsg = await parseErrorResponse(response);
            throw new Error(errorMsg);
        }

        const result = await response.json(); // Lấy phản hồi thành công từ server

        // 13.1.1.13 Hiển thị thông báo thêm sách thành công
        alert(`✅ Thêm sách thành công! ID = ${result.id}`);
        form.reset(); // Reset form sau khi thêm thành công
        hideAddProductForm(); // Đóng form lại

    } catch (err) {
        console.error("❌ Lỗi thêm sách:", err);
        alert("Có lỗi xảy ra:\n" + err.message);
    }
}

// Phân tích phản hồi lỗi từ server (nếu có)
async function parseErrorResponse(response) {
    try {
        const body = await response.clone().json(); // Đọc body JSON từ response clone

        if (body.validationErrors) {
            // Trả về các lỗi từng trường input
            return Object.entries(body.validationErrors)
                .map(([field, msg]) => `• ${field}: ${msg}`)
                .join("\n");
        }

        if (body.message) {
            // Trả về lỗi chung
            return body.message;
        }

        return `Lỗi ${response.status}`; // Mã lỗi HTTP (nếu không có gì khác)
    } catch {
        const text = await response.text(); // Trường hợp body không phải JSON
        return `Lỗi ${response.status}: ${text}`;
    }
}

// ===== DOM Ready =====
document.addEventListener("DOMContentLoaded", () => {
    hideAddProductForm(); // Ẩn form khi trang mới tải

    // Ẩn form khi click ra ngoài vùng form
    document.addEventListener("click", (e) => {
        const clickOutside = !form.contains(e.target) && !e.target.closest("button");
        if (overlay.style.display === "flex" && clickOutside) {
            hideAddProductForm();
        }
    });
});

