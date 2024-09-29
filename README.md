# Dating-App
Actors and role:
1. Người Dùng (User)
Đăng Ký và Đăng Nhập: Người dùng đăng ký và xác nhận email, sau đó đăng nhập vào ứng dụng.
Tạo và Quản Lý Hồ Sơ: Người dùng tạo và cập nhật hồ sơ, tải lên và quản lý ảnh.
Tương Tác Người Dùng: Người dùng thích và siêu thích hồ sơ của người khác, kết nối và gửi tin nhắn.
Sử Dụng Các Tính Năng Cao Cấp: Người dùng đăng ký các gói dịch vụ và sử dụng các lợi ích như Super Like và Boost.
5. Quản Lý Tài Khoản và Cài Đặt: Người dùng thiết lập tùy chọn và cài đặt, gửi tài liệu xác minh hồ sơ.
2. Quản Trị Viên (Admin)
Quản Lý Người Dùng: Kích hoạt, vô hiệu hóa, hoặc cấm người dùng.
Quản Lý Hồ Sơ và Xác Minh: Xác minh hồ sơ và quản lý nội dung.
Quản Lý Gói Dịch Vụ và Lợi Ích: Thêm, sửa, hoặc xóa các gói dịch vụ và lợi ích.
Quản Lý Giao Dịch: Theo dõi và quản lý các giao dịch mua hàng trong ứng dụng.
Phân Tích và Báo Cáo: Theo dõi và phân tích dữ liệu người dùng và kết nối, quản lý quảng cáo và doanh thu.

#Current Work-Flow:

1. Cải thiện Nghiệp Vụ Đăng Ký và Đăng Nhập
Xác thực Email: Gửi email xác thực khi người dùng đăng ký.
Đăng nhập bằng mạng xã hội: Cho phép người dùng đăng nhập bằng Google, Facebook, v.v.

2. Cải thiện Hồ Sơ Người Dùng
Cập nhật hồ sơ: Cho phép người dùng cập nhật hồ sơ với nhiều thông tin hơn như sở thích, nghề nghiệp, v.v.
Ảnh hồ sơ: Cho phép người dùng tải lên nhiều ảnh và chọn ảnh chính.

3. Cải thiện Tính Năng Tìm Kiếm và Gợi Ý
Tìm kiếm nâng cao: Cho phép người dùng tìm kiếm theo nhiều tiêu chí như độ tuổi, khoảng cách, sở thích, v.v.
Gợi ý thông minh: Sử dụng thuật toán học máy để gợi ý người dùng phù hợp dựa trên hành vi và sở thích.

4. Cải thiện Tính Năng Nhắn Tin
Nhắn tin thời gian thực: Sử dụng WebSocket để nhắn tin thời gian thực.
Thông báo đẩy: Gửi thông báo đẩy khi có tin nhắn mới.

5. Cải thiện Tính Năng Sự Kiện
Tạo và tham gia sự kiện: Cho phép người dùng tạo và tham gia các sự kiện.
Gợi ý sự kiện: Gợi ý sự kiện dựa trên sở thích và vị trí của người dùng.

6. Cải thiện Tính Năng Swipe
Swipe nâng cao: Cho phép người dùng xem thêm thông tin trước khi quyết định swipe.
Super Like: Thêm tính năng Super Like để người dùng thể hiện sự quan tâm đặc biệt.

7. Cải thiện Tính Năng Bảo Mật
Xác thực hai yếu tố (2FA): Thêm tính năng xác thực hai yếu tố để tăng cường bảo mật.
Báo cáo và chặn người dùng: Cho phép người dùng báo cáo và chặn người dùng khác.

8. Cải thiện Giao Diện Người Dùng
Giao diện thân thiện: Thiết kế giao diện thân thiện và dễ sử dụng.
Đa ngôn ngữ: Hỗ trợ nhiều ngôn ngữ để phục vụ người dùng quốc tế.

9. Cải thiện Hiệu Năng và Khả Năng Mở Rộng
Tối ưu hóa cơ sở dữ liệu: Tối ưu hóa truy vấn và cấu trúc cơ sở dữ liệu.
Sử dụng bộ nhớ đệm: Sử dụng Redis hoặc Memcached để tăng tốc độ truy xuất dữ liệu.

10. Cải thiện Tính Năng Thanh Toán
Thanh toán trực tuyến: Tích hợp các cổng thanh toán như PayPal, Stripe, v.v.
Gói dịch vụ: Cung cấp các gói dịch vụ với các tính năng nâng cao.


APIs List:
1) User Register:
 
![image](https://github.com/user-attachments/assets/b9c21484-2cfb-4598-b390-d9faed6bce53)

![image](https://github.com/user-attachments/assets/d8b8b60b-d263-4ad9-8850-f25855c338ab)


2) User Login:
- First login:

![image](https://github.com/user-attachments/assets/17441083-9d0c-4ada-8b22-cf38aa41d0e8)

- Second login to more:

![image](https://github.com/user-attachments/assets/72452cc6-dc2a-44dd-ac0d-4e24448aab98)



3) User Change-password:

![image](https://github.com/user-attachments/assets/89dfc9fc-eb1d-4096-8600-29959356fdc3)


4) User Forgot-password:

![image](https://github.com/user-attachments/assets/258dac0b-2b15-47ff-9213-c1519b1b4e01)

5) Logout:

![image](https://github.com/user-attachments/assets/39eb06c2-5830-4663-b01f-244635796070)

6) View profile:

![image](https://github.com/user-attachments/assets/8f8ad7d8-e55d-472b-8b08-11d07807659e)

Handle Error:

![image](https://github.com/user-attachments/assets/b746e059-22da-46d6-8659-39c5343cf8fc)

7) Update profile:
- First update: (update het)

![image](https://github.com/user-attachments/assets/b4ee65f6-3727-4a5b-bd8b-e5be256a18af)

- Second update to more: (update cai gi cung dc)

![image](https://github.com/user-attachments/assets/f6f01050-42f1-4c8e-b5a5-6a00b966ae9e)

8)

9)

10) Swipe:
![image](https://github.com/user-attachments/assets/06b9ad9a-e885-42ed-8a6d-67e2ddf317a6)


Task 29/9/24:
- Friend Management:
- Notification Management:
- Search Friend:
- Profile Management:
- Photo Management:
- Report and Ban user
- Activity Logging:
- Payment Integration:
- Event Management:
