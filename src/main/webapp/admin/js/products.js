/*--------------------------------------------------------
---------------------------------------------------------

                       Manager Product

---------------------------------------------------------
----------------------------------------------------------*/

//---------------Product Data--------------------//
// Lấy danh sách sản phẩm từ server
function fetchProducts() {
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-products', type: 'GET', dataType: 'json', success: function (products) {
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
                    url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/vi.json' // Ngôn ngữ Tiếng Việt
                }, paging: true, // Kích hoạt phân trang
                pageLength: 5, // Số bản ghi mỗi trang
                lengthChange: true, // Kích hoạt thay đổi số lượng bản ghi mỗi trang
            });
        }, error: function (xhr, status, error) {
            console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            alert("Không thể lấy danh sách sản phẩm. Vui lòng thử lại sau.");
        }
    });
}

/*--------------------------------------------------------
---------------------------------------------------------

                       Add Product

---------------------------------------------------------
----------------------------------------------------------*/
// ===== Form Hiển thị / Ẩn =====
const overlay = document.querySelector(".overlay-addProduct");
const form = document.getElementById("add-product-form");

// 13.1.1.0 Admin chọn “Thêm sách mới”
// 13.1.1.1 Hiển thị form nhập thông tin
function showAddProductForm() {
    overlay.style.display = "flex";
}

function hideAddProductForm() {
    form.reset();
    overlay.style.display = "none";
}

// ===== Lấy dữ liệu từ form =====
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

// 13.1.1.2 Nhập thông tin rồi nhấn lưu
async function createBook(event) {
    event.preventDefault();
    const data = getFormData();

    try {
        // 13.1.1.3 Gửi HTTP POST đến request /WebBanSach/api/books
        const response = await fetch("/WebBanSach/api/books", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const errorMsg = await parseErrorResponse(response);
            throw new Error(errorMsg);
        }

        const result = await response.json();
        alert(`✅ Thêm sách thành công! ID = ${result.id}`);
        form.reset();
        hideAddProductForm();

    } catch (err) {
        console.error("❌ Lỗi thêm sách:", err);
        alert("Có lỗi xảy ra:\n" + err.message);
    }
}

// ===== Xử lý lỗi từ server =====
async function parseErrorResponse(response) {
    try {
        const errJson = await response.json();
        return Object.entries(errJson)
            .map(([field, msg]) => `• ${field}: ${msg}`)
            .join("\n");
    } catch {
        const text = await response.text();
        return `Lỗi ${response.status}: ${text}`;
    }
}

// ===== DOM Ready =====
document.addEventListener("DOMContentLoaded", () => {
    hideAddProductForm();

    document.addEventListener("click", (e) => {
        const clickOutside = !form.contains(e.target) && !e.target.closest("button");
        if (overlay.style.display === "flex" && clickOutside) {
            hideAddProductForm();
        }
    });
});

