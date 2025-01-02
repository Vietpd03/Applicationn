MyFShop

Giới thiệu

MyFShop là một ứng dụng thương mại điện tử cung cấp nền tảng để người dùng mua sắm và quản lý sản phẩm. 

Công nghệ sử dụng

1. Ngôn ngữ lập trình

Kotlin: Ngôn ngữ chính để phát triển ứng dụng Android.

2. Công cụ và thư viện

Firebase:

Firestore: Lưu trữ dữ liệu thời gian thực.

Firebase Authentication: Quản lý người dùng.

Firebase Storage: Lưu trữ hình ảnh sản phẩm.


OkHttp: Thư viện HTTP client để thực hiện các yêu cầu API.

Android Jetpack:

ViewModel, LiveData: Quản lý vòng đời và dữ liệu UI.

Navigation Component: Điều hướng giữa các màn hình.

RecyclerView: Hiển thị danh sách dữ liệu.

3. Thiết kế giao diện

XML Layouts: Xây dựng giao diện người dùng.

Material Design: Tạo trải nghiệm người dùng hiện đại và thân thiện.

Các tính năng chính

Đăng ký và đăng nhập: Sử dụng Firebase Authentication.

Quản lý sản phẩm:

Thêm, chỉnh sửa và xóa sản phẩm.

Xem danh sách sản phẩm đã bán.


Hiển thị trạng thái thanh toán (thành công, hủy, lỗi).

Thông báo: Hiển thị thông báo lỗi hoặc thành công khi thực hiện các thao tác.

Cấu trúc thư mục

myfshop/
├── ui/
│   ├── activities/       # Các màn hình chính (MainActivity, LoginActivity,...)
│   ├── fragments/        # Các màn hình con (SoldProductsFragment,...)
│   ├── adapters/         # Các adapter cho RecyclerView.
├── models/               # Các lớp dữ liệu (User, Product, SoldProduct,...).
├── firestore/            # Các thao tác với Firebase Firestore.
├── utils/                # Các hàm tiện ích (Constants, Validators,...).
├── Constant/             # Các hằng số cấu hình (AppInfo,...).
└── Helper/               # Các tiện ích hỗ trợ (HMacUtil, HexStringUtil,...).

Hướng dẫn cài đặt

1. Yêu cầu hệ thống

Android Studio Arctic Fox trở lên.

Thiết bị chạy Android 5.0 (API 21) trở lên. (Thiết bị demo máy ảo pixel 3a api 35 và pixel 3 api 35)


2. Thiết lập Firebase

Truy cập Firebase Console.

Tạo một dự án mới và thêm tệp google-services.json vào thư mục app/.

Kích hoạt Firestore, Authentication và Storage trong dự án Firebase.


3. Chạy ứng dụng

Mở dự án bằng Android Studio.

Đồng bộ Gradle và chạy ứng dụng trên thiết bị hoặc trình giả lập.

Ghi chú

Cấu hình Firebase Firestore (quyền truy cập, cấu trúc dữ liệu).

thông tin đăng nhập: 

tài khoảng admin: user: admin@gmail.com // Pass: 123456
tài khoản user có thể tự tạo theo ý (khi tạo tài khoảng mới mặt định là user, admin chỉ có 1 tài khoảng hoặt thiệt lập trong quản lý user của admin)