import { jwtDecode } from "jwt-decode"; // Import thư viện jwt-decode để giải mã JWT token
import { instance, Login } from "./api-instance-provider"; // Import `instance` để gọi API và `Login` cho URL

// Hàm lưu token, email, role và userId vào localStorage
const saveTokenAndUserInfo = (token, email, role) => {
  if (token) {
    localStorage.setItem("userToken", token);
    localStorage.setItem("email", email);
    localStorage.setItem("role", role);

    // Giải mã token để lấy userId
    const decodedToken = jwtDecode(token);
    const userId = decodedToken.userId;

    if (userId) {
      localStorage.setItem("userId", userId); // Lưu userId vào localStorage
    }
  }
};

// Phương thức xử lý đăng nhập
export const loginUser = async (email, password) => {
  try {
    // Gửi yêu cầu đăng nhập với email và password
    const response = await instance.post(Login.ORIGIN, { email, password });

    // Nếu trạng thái từ server trả về là 200 (đăng nhập thành công)
    if (response.data.status === 200) {
      const resultMessage = response.data.message;
      const data = response?.data?.data;
      const token = data?.token; // Token sẽ chứa trong `data`
      const role = data?.role;
      console.log("🚀 ~ loginUser ~ response.data.data:", response.data.data);

      // Lưu token và thông tin user vào localStorage
      saveTokenAndUserInfo(token, email, role);
      if (role === "Admin") {
        alert("Admin login successful!");
        return "Admin login"; // Điều hướng tới layout admin
      } else { // Role === User
        // Xử lý các trường hợp đăng nhập khác nhau
        if (resultMessage.startsWith("First login")) {
          alert("First login detected, please change your password.");
          return "First login"; // Điều hướng tới trang đổi mật khẩu
        } else if (resultMessage.startsWith("Second login")) {
          alert("Second login detected, please update your profile.");
          return "Second login"; // Điều hướng tới trang cập nhật hồ sơ
        } else if (resultMessage === "Login successful") {
          alert("Login successful!");
          return "Login successful"; // Điều hướng tới trang homePage
        } else {
          alert("Unknown response from server.");
          return "Unknown"; // Xử lý nếu thông báo không rõ ràng
        }
      }
    } else {
      alert(response.data.message || "Đăng nhập thất bại");
      return "Failed"; // Xử lý khi đăng nhập thất bại
    }
  } catch (error) {
    // Xử lý lỗi phát sinh trong quá trình request
    alert("Login failed! Please check your email and password.");
    return "Error"; // Trả về nếu có lỗi
  }
};
