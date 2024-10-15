<template>
  <div>
    <div class="sidebar">
      <!-- Left Sidebar (LoveBellSidebar) -->
      <div class="sidebar-content">
        <LoveBellSidebar />
      
        <!-- Right Content (Profile Header and Photo Grid) -->
        <div class="content">
          <!-- Profile Header -->
          <div class="profile-header">
            <img :src="profileData.avatar" alt="Profile Image" class="profile-image" />
            <div class="profile-info">
              <h2>{{ profileData.name }} - {{ profileData.age }} </h2>
              <h5>{{ profileData.gender }}</h5>
              <p>{{ profileData.bio }}</p>
            </div>
            <div class="edit-profile">
              <button @click="showEditProfile = true">Chỉnh sửa trang cá nhân</button>
            </div>
          </div>  

          <!-- Photo Grid -->
          <div class="photo-grid">
            <div v-for="(photo, index) in profileData.photos" :key="index" class="photo-item">
              <img :src="photo.url" alt="Photo Thumbnail" class="photo-thumbnail" />
              <p class="photo-title">Photo {{ index + 1 }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal hiển thị trang chỉnh sửa -->
    <div v-if="showEditProfile" class="modal-overlay">
      <div class="modal-content">
        <!-- Nút X để đóng modal -->
        <button class="close-button" @click="showEditProfile = false">X</button>
        
        <ChangeProfilePage @close="showEditProfile = false" />
      </div>
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";
import ChangeProfilePage from "@/views/ChangeProfilePage.vue"; // Import trang chỉnh sửa
import { getMyProfile } from "@/services/viewProfile-service.js"; // Import hàm lấy profile

export default {
  data() {
    return {
      profileData: {
        avatar: "", // Avatar URL
        name: "", // Name
        age: "", // Age
        gender: "", // Gender
        bio: "", // Bio
        photos: [] // Photos array
      },
      showEditProfile: false // Trạng thái hiển thị modal chỉnh sửa
    };
  },
  components: {
    LoveBellSidebar,
    ChangeProfilePage // Đăng ký component trang chỉnh sửa
  },
  async mounted() {
    try {
      // Gọi API lấy thông tin profile người dùng
      const profileResponse = await getMyProfile();
      const profile = profileResponse.data; // Lấy data từ response
      
      // Gán dữ liệu từ API vào profileData
      this.profileData.avatar = profile.avatar || "Unnamed User";
      this.profileData.name = profile.name || "Unnamed User";
      this.profileData.age = profile.age || "Unknown";
      this.profileData.gender = profile.gender || "Unknown";
      this.profileData.bio = profile.bio || "No bio available";
      this.profileData.photos = profile.photos || [];
    } catch (error) {
      console.error("Error loading profile data:", error);
    }
  }
};
</script>

<style>
/* Các style không thay đổi */
.sidebar {
  flex-direction: column;
  height: 100vh;
}

.edit-profile {
  position: absolute;
  right: 300px;
  top: 100px;
}

.sidebar-content {
  display: flex;
  width: 100%;
  height: 100%;
}

.content {
  flex: 5;
  padding-left: 200px;
  padding-top: 50px;
}

/* Profile Header */
.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  padding-left: 70px;
}

.profile-image {
  width: 170px;
  height: 170px;
  border-radius: 50%;
  margin-right: 20px;
}

.profile-info {
 font-size: x-large;
}

/* Photo Grid */
.photo-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
}

.photo-item {
  text-align: center;
}

.photo-thumbnail {
  width: 67%;
  height: auto;
  border-radius: 8px;
  object-fit: cover;
}

.photo-title {
  margin-top: 5px;
  font-size: 14px;
  font-weight: bold;
}

/* Style cho modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 12px;
  max-width: 600px;
  width: 100%;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

/* Style cho nút X (close) */
.close-button {
  position: absolute;
  top: 10px;
  right: 15px;
  background-color: transparent;
  border: none;
  font-size: 20px;
  font-weight: bold;
  cursor: pointer;
}

.close-button:hover {
  color: red;
}
</style>
