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
      <p class="mt-4" v-if="location" v-html="location"></p> <!-- Sử dụng v-html để hiển thị vị trí -->

      <!-- Thêm bản đồ -->
      <div id="map" class="map" v-if="latitude && longitude"></div>

      <button class="save-location-button" @click="saveLocation" v-if="latitude && longitude">
        Save Location
      </button>
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";
import { saveLocationToDB } from '@/services/admin/admin-location-service'; // Assume you have a service for handling API requests
import L from "leaflet";
import "leaflet/dist/leaflet.css";

export default {
  components: {
    LoveBellSidebar,
  },
  data() {
    return {
      location: null, // Biến để lưu thông tin vị trí
      latitude: null,  // Lưu trữ vĩ độ
      longitude: null, // Lưu trữ kinh độ
      map: null,      // Biến bản đồ
      mapInitialized: false, // Biến trạng thái để kiểm tra xem bản đồ đã được khởi tạo chưa
    };
  },
  methods: {
    async getLocation() {
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
      this.latitude = position.coords.latitude;
      this.longitude = position.coords.longitude;
      this.location = `Latitude: ${this.latitude}, Longitude: ${this.longitude}`;

      // Gọi hàm để lấy địa chỉ
      this.getAddress(this.latitude, this.longitude);

      this.$nextTick(() => {
        this.initMap(); // Khởi tạo bản đồ khi có vị trí
      });
    },

    async getAddress(lat, lng) {
      try {
        const response = await fetch(`https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lng}&format=json`);
        const data = await response.json();
        if (data && data.display_name) {
          this.location += `<br/> Address: ${data.display_name}`; // Cập nhật địa chỉ vào biến location
        }
      } catch (error) {
        console.error("Error fetching address:", error);
      }
    },

    initMap() {
      if (this.mapInitialized) {
        // Nếu bản đồ đã được khởi tạo, không cần khởi tạo lại
        this.map.setView([this.latitude, this.longitude], 13);
      } else {
        // Khởi tạo bản đồ lần đầu tiên
        this.map = L.map("map").setView([this.latitude, this.longitude], 13);

        // Thêm lớp bản đồ từ OpenStreetMap
        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
          maxZoom: 19,
          attribution: '© OpenStreetMap',
        }).addTo(this.map);

        // Thêm marker tại vị trí
        L.marker([this.latitude, this.longitude]).addTo(this.map)
            .bindPopup("You are here")
            .openPopup();

        this.mapInitialized = true; // Đánh dấu là bản đồ đã được khởi tạo
      }
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

  async saveLocation() {
    try {
      // Retrieve the userId from localStorage
      const userId = localStorage.getItem('userId');

      if (!userId) {
        alert('User is not logged in.');
        return;
      }

      // Remove the 'Address: ' prefix and split the address by commas
      const addressString = this.location.replace('Address: ', '');
      const addressParts = addressString.split(', ');

      // Map each part to its corresponding entity field
      const ward = addressParts[0] || '';                // Phường Hòa Quý
      const district = addressParts[1] || '';            // Ngũ Hành Sơn District
      const province = addressParts[2] || '';            // Da Nang
      const postalCode = addressParts[3] || '';          // 50507 (optional if required in your entity)
      const country = addressParts[4] || '';             // Vietnam

      // Create the location data object with latitude, longitude, and the parsed address components
      const locationData = {
        latitude: this.latitude,
        longitude: this.longitude,
        street: '',       // No street in the example, leave it empty if it's not present
        ward: ward,
        district: district,
        province: province,
        postalCode: postalCode,
        country: country,
        userId: userId,    // Retrieve userId from localStorage
      };

      // Save the location data to the database
      await saveLocationToDB(locationData);

      alert('Location saved successfully!');
    } catch (error) {
      console.error('Error saving location:', error);
      alert('Failed to save location');
    }
  }
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

/* Styling cho bản đồ */
.map {
  height: 400px; /* Chiều cao của bản đồ */
  margin-top: 20px;
}

.save-location-button {
  background-color: #28a745;
  border: none;
  padding: 10px 20px;
  color: white;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
}

.save-location-button:hover {
  background-color: #218838;
  transform: scale(1.05); /* Adds a zoom effect */
}
</style>
