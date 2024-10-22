import { instance } from "./api-instance-provider";


// Hàm để tải một hồ sơ ngẫu nhiên
export async function loadRandomProfile() {
  try {
    const response = await instance.get('/profiles/random', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('userToken')}`,
      },
    });

    // Log the response to see the structure
    console.log("API Response:", response);

    // Ensure response contains the expected data
    if (!response.data || !response.data.profileId) {
      throw new Error("Invalid API response format");
    }

    // Adjust the returned profile format to match the expected output (even though it's a single object)
    const profile = {
      ...response.data,
      imageUrl: response.data.avatar || response.data.imageUrl, // Adjust based on actual API response
    };

    // Return the profile in an array format to keep the rest of the code compatible
    return [profile];
  } catch (error) {
    console.error("Error loading random profiles:", error);
    throw error;
  }
}

// Hàm để gọi API tải tất cả hồ sơ người dùng
export async function loadAllProfiles() {
  try {
    const response = await instance.get(`/profiles`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("userToken")}`, // Gửi token trong header
      },
    });
    // Đảm bảo ánh xạ đúng trường hình ảnh
    return response.data.data.map((profile) => ({
      ...profile,
      imageUrl: profile.avatar || profile.imageUrl, // Sử dụng avatar hoặc imageUrl tuỳ thuộc vào API
    }));
  } catch (error) {
    console.error("Error loading all profiles:", error);
    throw error;
  }
}
