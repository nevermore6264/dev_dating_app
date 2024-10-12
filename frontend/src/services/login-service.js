import { jwtDecode } from "jwt-decode"; // Import thư viện jwt-decode để giải mã JWT token
import { instance, Login } from "./api-instance-provider"; // Import `instance` để gọi API và `Login` cho URL

// Phương thức xử lý đăng nhập
export const loginUser = async (email, password) => {
  try {
    // Gửi yêu cầu đăng nhập với email và password
    const response = await instance.post(Login.ORIGIN, {
      email,
      password,
    });

    // Nếu trạng thái từ server trả về là 200 (đăng nhập thành công)
    if (response.data.status === 200) {
      const resultMessage = response.data.message;
      const token = response?.data?.data?.token; // Token sẽ chứa trong `data`

      console.log("🚀 ~ loginUser ~ response.data.data:", response.data.data);
      // Xử lý các trường hợp từ server
      if (resultMessage.startsWith("First login")) {
        alert("First login detected, please change your password.");

        // Lưu token vào localStorage
        if (token) {
          localStorage.setItem("userToken", token);
          localStorage.setItem("email", email);

          // Giải mã token để lấy userId
          const decodedToken = jwtDecode(token);
          const userId = decodedToken.userId;

          if (userId) {
            localStorage.setItem("userId", userId); // Lưu userId vào localStorage
          }
        }

        // Điều hướng người dùng tới trang thay đổi mật khẩu
        return "First login"; // Trả về để phía frontend điều hướng tới trang đổi mật khẩu
      } else if (resultMessage.startsWith("Second login")) {
        alert("Second login detected, please update your profile.");

        // Lưu token vào localStorage
        if (token) {
          localStorage.setItem("userToken", token);
          localStorage.setItem("email", email);

          // Giải mã token để lấy userId
          const decodedToken = jwtDecode(token);
          const userId = decodedToken.userId;

          if (userId) {
            localStorage.setItem("userId", userId); // Lưu userId vào localStorage
          }
        }

        // Điều hướng người dùng tới trang cập nhật thông tin
        return "Second login"; // Trả về để phía frontend điều hướng tới trang cập nhật hồ sơ
      } else if (resultMessage === "Login successful") {
        alert("Login successful!");

        // Lưu token vào localStorage
        if (token) {
          localStorage.setItem("userToken", token);
          localStorage.setItem("email", email);

          // Giải mã token để lấy userId
          const decodedToken = jwtDecode(token);
          const userId = decodedToken.userId;

          if (userId) {
            localStorage.setItem("userId", userId); // Lưu userId vào localStorage
          }
        }

        // Điều hướng người dùng tới trang homePage
        return "Login successful"; // Trả về để phía frontend điều hướng tới trang homePage
      } else {
        alert("Unknown response from server.");
        return "Unknown"; // Trả về nếu thông báo không rõ ràng
      }
    } else {
      alert(response.data.message || "Đăng nhập thất bại");
      return "Failed"; // Trả về nếu đăng nhập thất bại
    }
  } catch (error) {
    // Xử lý lỗi phát sinh trong quá trình request
    alert(
      "Login failed! Please check your email and password." 
    );
    return "Error"; // Trả về thông tin lỗi
  }
};
