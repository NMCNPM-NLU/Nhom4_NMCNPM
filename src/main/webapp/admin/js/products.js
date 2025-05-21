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

// Hiển thị form thêm người dùng
function showAddProductForm() {
    const overlay = document.querySelector(".overlay-addProduct");
    overlay.style.display = "flex"; // Hiển thị lớp phủ
}


function hideAddProductForm() {
    const overlay = document.querySelector(".overlay-addProduct");
    const form = document.getElementById("add-product-form");
    form.reset(); // Xóa dữ liệu trong form
    overlay.style.display = "none";
}


async function createBook(event) {
    event.preventDefault();

    const form = document.getElementById('add-product-form');
    const formData = new FormData(form);

    const data = {
        title: formData.get('title'),
        author: formData.get('author'),
        description: formData.get('description'),
        publishedDate: formData.get('publishedDate'),
        price: parseFloat(formData.get('price')),
        stockQty: parseInt(formData.get('stockQty')),
        imageUrl: formData.get('imageUrl'),
        categoryIds: Array.from(form.querySelector('#categoryIds').selectedOptions, o => parseInt(o.value))
    };

    console.log('Gửi lên server:', JSON.stringify(data));

    try {
        // 13.1.1.3 Gửi HTTP POST đến request /api/books
        const resp = await fetch('/WebBanSach/api/books', {
            method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data)
        });

        if (!resp.ok) {
            let errJson;
            try {
                errJson = await resp.json(); // Thử parse JSON
            } catch {
                // Trường hợp response không phải JSON (ví dụ: lỗi 500)
                // 13.1.2.3
                // const text = await resp.text();
                // throw new Error(`Server error: ${resp.status} - ${text}`);
                const text = await resp.text();
                throw new Error(`Server error: ${resp.status} - ${text}`);
            }

            // Xử lý error object từ server (ghép các message)
            const errorMessage = Object.entries(errJson)
                .map(([field, msg]) => `• ${field}: ${msg}`)
                .join('\n');

            // 13.1.2.8 throw new Error(errorMessage || 'Lỗi không xác định');
            throw new Error(errorMessage || 'Lỗi không xác định');
        }

        // Ở đây chắc chắn server trả đúng JSON { id: ... }
        const result = await resp.json();
        // 13.1.1.12  alert(`Thêm sách thành công! ID = ${result.id}`)
        alert(`Thêm sách thành công! ID = ${result.id}`);
        form.reset();
    } catch (e) {
        console.error(e);
        // 13.1.2.12  alert(`Có lỗi khi thêm sách:\n` + e.message)
        alert('Có lỗi khi thêm sách:\n' + e.message);
    }
}


window.addEventListener("DOMContentLoaded", hideAddProductForm);

// Xử lý sự kiện click ra ngoài form để ẩn form
document.addEventListener("click", function (event) {
    const overlay = document.querySelector(".overlay-addProduct");
    const form = document.getElementById("add-product-form");

    // Kiểm tra nếu form đang hiển thị và click không nằm trong form hoặc nút hiển thị form
    if (overlay.style.display === "flex" && !form.contains(event.target) && !event.target.closest("button")) {
        hideAddProductForm();
    }
});

