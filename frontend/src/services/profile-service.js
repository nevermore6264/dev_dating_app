import { instance } from "./api-instance-provider";

// export const getRandomUserProfile = async () => {
//   try {
//     const response = await instance.get('/profiles/random', {
//       headers: {
//         Authorization: `Bearer ${localStorage.getItem('userToken')}`,
//       },
//     });
//     console.log('Response received:', response);

//     return response.data;
//   } catch (error) {
//     console.error('Error fetching random profile:', error);
//     throw error;
//   }
// };

// Hàm để tải một hồ sơ ngẫu nhiên
export async function loadRandomProfile() {
  try {
    const response = await instance.get('/profiles/random', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('userToken')}`,
      },
    });

    if (response.status === 204) {
      console.warn('No random profile available.');
      return null;
    }

    const profile = response.data;
    return {
      profileId: profile.profileId,
      userId: profile.userId,
      imageUrl: profile.avatar || profile.imageUrl,
      name: profile.name,
      age: profile.age,
      bio: profile.bio,
    };
  } catch (error) {
    if (error.response && error.response.status === 401) {
      console.error('Unauthorized access. Token might be missing or invalid.');
      alert('Không có quyền truy cập. Vui lòng đăng nhập lại.');
    } else {
      console.error('Error loading random profile:', error);
      alert('Không thể tải hồ sơ, vui lòng thử lại sau.');
    }
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
