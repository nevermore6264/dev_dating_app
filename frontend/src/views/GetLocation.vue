<template>
  <div class="emergency-page-container">
    <!-- Sidebar -->
    <LoveBellSidebar />

    <!-- Nội dung chính của trang -->
    <div class="emergency-page">
      <!-- Tiêu đề trang -->
      <div class="header">
        <h1>GET CURRENT LOCATION</h1>
        <button class="emergency-button" @click="getLocation">Get Location</button>
      </div>
      <p v-if="location">{{ location }}</p> <!-- Hiển thị vị trí -->
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";

export default {
  components: {
    LoveBellSidebar,
  },
  data() {
    return {
      location: null, // Biến để lưu thông tin vị trí
    };
  },
  methods: {
    getLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
              this.showPosition(position);
            },
            (error) => {
              this.handleError(error);
            }
        );
      } else {
        this.location = "Geolocation is not supported by this browser.";
      }
    },
    showPosition(position) {
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;
      this.location = `Latitude: ${latitude}, Longitude: ${longitude}`;
    },
    handleError(error) {
      switch (error.code) {
        case error.PERMISSION_DENIED:
          this.location = "User denied the request for Geolocation.";
          break;
        case error.POSITION_UNAVAILABLE:
          this.location = "Location information is unavailable.";
          break;
        case error.TIMEOUT:
          this.location = "The request to get user location timed out.";
          break;
        case error.UNKNOWN_ERROR:
          this.location = "An unknown error occurred.";
          break;
      }
    },
  },
};
</script>

<style scoped>
/* Container Flexbox để chứa sidebar và phần chính */
.emergency-page-container {
  display: flex;
  align-items: flex-start;
}

.emergency-page {
  flex: 1;
  padding: 20px;
  text-align: center;
  font-family: Arial, sans-serif;
}

.header h1 {
  color: #ff33cc;
  font-size: 48px;
  font-weight: bold;
  margin: 10px 0;
}

.emergency-button {
  background-color: #ff0000;
  border: 2px solid #000;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  color: #fff;
  font-weight: bold;
}
</style>
