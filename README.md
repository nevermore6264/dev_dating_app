# Dating-App
Các Thực Thể Chính và Mối Quan Hệ Mở Rộng
1. User (Người Dùng)
user_id (PK)
email
password_hash
created_at
last_login
status (active, inactive, banned)
2. Profile (Hồ Sơ)
profile_id (PK)
user_id (FK)
name
age
gender
bio
location
created_at
updated_at
3. Photo (Ảnh)
photo_id (PK)
profile_id (FK)
url
created_at
4. Like (Thích)
like_id (PK)
user_id (FK)
profile_id (FK)
created_at
5. Match (Kết Nối)
match_id (PK)
user1_id (FK)
user2_id (FK)
created_at
6. Message (Tin Nhắn)
message_id (PK)
match_id (FK)
sender_id (FK)
content
created_at
status (sent, delivered, read)
7. User Preferences (Tùy Chọn Người Dùng)
preference_id (PK)
user_id (FK)
age_range
gender_preference
distance_preference
8. User Settings (Cài Đặt Người Dùng)
setting_id (PK)
user_id (FK)
notifications_enabled
privacy_level
9. Profile Verification (Xác Minh Hồ Sơ)
verification_id (PK)
profile_id (FK)
status (pending, verified, rejected)
document_url
created_at
updated_at
10. User Analytics (Phân Tích Người Dùng)
analytics_id (PK)
user_id (FK)
likes_received
matches_made
messages_sent
11. Match Analytics (Phân Tích Kết Nối)
analytics_id (PK)
match_id (FK)
success_rate
12. Subscription (Gói Đăng Ký)
subscription_id (PK)
user_id (FK)
plan (free, premium, gold)
start_date
end_date
13. Super Like (Siêu Thích)
superlike_id (PK)
user_id (FK)
profile_id (FK)
created_at
14. Boost (Tăng Cường)
boost_id (PK)
user_id (FK)
start_time
end_time
15. Thực Thể Subscription Plan (Gói Đăng Ký)
a. Subscription Plan (Gói Đăng Ký)
plan_id (PK)
name (Tên gói, ví dụ: Free, Premium, Gold)
price (Giá của gói)
duration (Thời gian hiệu lực của gói, ví dụ: 1 tháng, 1 năm)
description (Mô tả về gói)
b. Subscription Benefits (Lợi Ích Gói Đăng Ký)
benefit_id (PK)
plan_id (FK)
benefit_name (Tên lợi ích, ví dụ: Super Like, Boost)
benefit_description (Mô tả lợi ích)
quantity (Số lượng lợi ích, ví dụ: 5 Super Likes mỗi ngày)

Subscription Plan
-----------------
plan_id (PK)
name
price
duration
description

Subscription Benefits
---------------------
benefit_id (PK)
plan_id (FK)
benefit_name
benefit_description
quantity

User
-----
user_id (PK)
email
password_hash
created_at
last_login
status

Profile
-------
profile_id (PK)
user_id (FK)
name
age
gender
bio
location
created_at
updated_at

Photo
-----
photo_id (PK)
profile_id (FK)
url
created_at

Like
----
like_id (PK)
user_id (FK)
profile_id (FK)
created_at

Match
-----
match_id (PK)
user1_id (FK)
user2_id (FK)
created_at

Message
-------
message_id (PK)
match_id (FK)
sender_id (FK)
content
created_at
status

User Preferences
----------------
preference_id (PK)
user_id (FK)
age_range
gender_preference
distance_preference

User Settings
-------------
setting_id (PK)
user_id (FK)
notifications_enabled
privacy_level

Profile Verification
--------------------
verification_id (PK)
profile_id (FK)
status
document_url
created_at
updated_at

User Analytics
--------------
analytics_id (PK)
user_id (FK)
likes_received
matches_made
messages_sent

Match Analytics
---------------
analytics_id (PK)
match_id (FK)
success_rate

Subscription
-------------
subscription_id (PK)
user_id (FK)
plan_id (FK)
start_date
end_date

Super Like
-----------
superlike_id (PK)
user_id (FK)
profile_id (FK)
created_at

Boost
------
boost_id (PK)
user_id (FK)
start_time
end_time
Workflow Tổng Quan
Dưới đây là mô tả tổng quan về workflow của ứng dụng:
1. Đăng Ký và Đăng Nhập:
Người dùng đăng ký và xác nhận email.
Người dùng đăng nhập và được chuyển hướng đến trang chính.
Tạo và Quản Lý Hồ Sơ:
Người dùng tạo và cập nhật hồ sơ.
Người dùng tải lên và quản lý ảnh.
3. Tương Tác Người Dùng:
Người dùng thích và siêu thích hồ sơ của người khác.
Khi hai người dùng thích nhau, họ kết nối và có thể gửi tin nhắn.
Sử Dụng Các Tính Năng Cao Cấp:
Người dùng đăng ký các gói dịch vụ và sử dụng các lợi ích như Super Like và Boost.
5. Quản Lý Tài Khoản và Cài Đặt:
Người dùng thiết lập tùy chọn và cài đặt.
Người dùng gửi tài liệu xác minh hồ sơ.
6. Phân Tích và Báo Cáo:
Hệ thống thu thập và lưu trữ dữ liệu phân tích người dùng và kết nối.
