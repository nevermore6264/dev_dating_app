<template>
  <div class="app-wrapper">
    <!-- Sidebar component -->
    <LoveBellSidebar />

    <!-- Main content area -->
    <div class="main-content">
      <!-- Page Title (same row as sidebar) -->
      <div class="page-title">
        <h2>LOVE BELL RADAR</h2>
      </div>

      <!-- Information and button section -->
      <div class="info-section">
        <div class="info">
          <h3>Looking for someone?</h3>
          <p>{{ users.length }} users are close to you, let's find them !!</p>
          <p>Within {{ range }}m</p>
          <p>
            Make sure to look and check, then decide whether you should catch
            up with them!
          </p>
        </div>
        <button
            class="scan-btn"
            @click="startScanning"
            :disabled="isScanning"
        >
          {{ isScanning ? "Scanning..." : "Re-scanning" }}
        </button>

        <!-- Profile Popup (conditional rendering) -->
        <div v-if="selectedUser" class="modal-overlay-map">
          <div class="modal-content full-image-modal" @click.stop>
            <button class="close-button" @click="closeProfile">
              <i class="fas fa-times"></i>
            </button>
            <h2>{{ currentProfile.name }} - {{ currentProfile.age }}</h2>
            <p>{{ currentProfile.bio }}</p>
            <div class="images-wrapper">
              <div class="image-item" v-for="(image, index) in currentProfile.images" :key="index">
                <img :src="image" :alt="'Photo ' + (index + 1)" class="profile-image-full" />
                <span class="image-label">Photo {{ index + 1 }}</span>
              </div>
            </div>
            <div class="action-buttons-modal">
              <button class="button dislike-button" @click="dislike">
                <i class="fas fa-times"></i>
              </button>
              <button class="button super-like-button" @click="superLike">
                <i class="fas fa-star"></i>
              </button>
              <button class="button like-button" @click="like">
                <i class="fas fa-heart"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";
import { checkUserLocation } from '@/services/location-service';
import { ElNotification } from "element-plus";

export default {
  components: {
    LoveBellSidebar,
  },
  data() {
    return {
      range: 500, // Distance range
      users: [], // Store user positions
      isScanning: false, // Controls scanning animation
      selectedUser: null, // Stores the user clicked for profile display
      currentProfile: {
        name: 'Le Quang Huy',
        age: 22,
        bio: 'This is a sample bio for you.',
        images: [
          'https://via.placeholder.com/200x200.png?text=Photo+1',
          'https://via.placeholder.com/200x200.png?text=Photo+2',
          'https://via.placeholder.com/200x200.png?text=Photo+3'
        ]
      },
      userId: localStorage.getItem('userId'),
    };
  },
  mounted() {
    this.checkUserLocation();
    this.randomizeUsers(); // Initialize random user positions when the app loads
  },
  methods: {
    randomizeUsers() {
      // Create random user positions (x, y between 0 and 1)
      this.users = Array.from({ length: 6 }, () => ({
        id: Math.random().toString(36).substring(7), // Generate random ID
      }));
    },
    startScanning() {
      // Start scanning, enable the animation
      this.isScanning = true;
      this.selectedUser = null; // Hide any active profile

      // Call your API to get nearby users here
      // Example: this.getNearbyUsers();

      // Randomize user positions
      this.randomizeUsers();
    },
    showProfile(user) {
      // Set the selected user to display their profile
      this.selectedUser = user;
    },
    closeProfile() {
      // Close the profile popup
      this.selectedUser = null;
    },
    dislike() {
      alert("Disliked!");
    },
    superLike() {
      alert("Super Liked!");
    },
    like() {
      alert("Liked!");
    },
    async checkUserLocation() {
      try {
        const userId = localStorage.getItem('userId');
        const response = await checkUserLocation(userId);

        // If the location is not set (you can customize based on your API response)
        if (response?.message === "Location not configured for user" ) {
          // Show notification using Element Plus
          ElNotification({
            title: 'Missing Location',
            message: 'Please set your location to continue using Love Bell Radar.',
            type: 'warning',
          });

          // Redirect to the location setup page after a delay (2 seconds)
          setTimeout(() => {
            this.$router.push('/getLocation'); // Redirect to location page
          }, 2000);
        }
      } catch (error) {
        console.error("Error checking user location:", error);
      }
    },
  },
};
</script>

<style scoped>
/* Wrapper for the whole app */
.app-wrapper {
  display: flex;
  height: 100vh;
}

/* Sidebar */
.sidebar {
  width: 250px;
}

/* Main content area */
.main-content {
  display: flex;
  flex-direction: column; /* Bố trí theo cột cho tiêu đề và phần radar/info */
  flex: 1;
  padding-left: 20px; /* Thụt vào ngang với sidebar */
}

.page-title {
  display: flex;
  align-items: center;
  padding: 15px 40px;
  width: 50%; /* Nằm ngang với phần còn lại của nội dung */
}

.page-title h2 {
  font-size: 35px;
  color: #ff33cc;
  margin: 35px;
}

hr {
  border: none; /* Loại bỏ viền mặc định */
  height: 2px; /* Độ dày của hr */
  background-color: #ff66ff; /* Màu sắc */
  margin: 20px 0; /* Khoảng cách trên và dưới */
}

/* Information section and button */
.info-section {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.info {
  font-size: 21px;
}

.info h3 {
  font-weight: bold;
  font-size: 35px;
}

.scan-btn {
  width: 30%;
  padding: 10px 20px;
  font-size: 20px;
  background-color: #ff99ff;
  border: none;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px;
}

.scan-btn:disabled {
  background-color: #ddd;
}

.scan-btn:hover:not(:disabled) {
  background-color: #ff66ff;
}

/* Modal Overlay */
.modal-overlay-map {
  position: fixed;
  top: 0;
  left: 0;
  width: auto;
  height: auto;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  margin-left: 1000px;
  margin-top: 360px;
}

/* Modal Content */
.modal-content {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  position: relative;
  max-width: 90%;
  max-height: 90%;
  overflow: auto;
  width: 700px;
}

/* Close Button */
.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.5rem;
}

.close-button:hover {
  transform: scale(1.1);
}

.close-button:active {
  transform: scale(0.95);
}

/* Image Wrapper */
.images-wrapper {
  display: flex;
  gap: 20px;
  margin-top: 20px;
  justify-content: center;
}

.image-item {
  flex: 1;
  text-align: center;
  max-width: 200px;
}

.profile-image-full {
  width: 100%;
  height: auto;
  border-radius: 10px;
  object-fit: cover;
}

.image-label {
  margin-top: 5px;
  font-size: 14px;
}

.action-buttons-modal {
  display: flex;
  justify-content: center;
  gap: 40px;
  margin-top: 20px;
}

.button {
  position: relative;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: white;
  border: 3px solid #ddd; /* Thêm viền cho nút */
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.button:hover {
  background-color: #f0f0f0; /* Thay đổi màu nền khi hover */
  border-color: #ff99ff; /* Đổi màu viền khi hover */
}

.button i {
  font-size: 24px; /* Kích thước biểu tượng */
}
</style>
