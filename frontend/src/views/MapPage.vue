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
          <label for="range-input">Within
            <input
                type="number"
                id="range-input"
                v-model="range"
                min="50"
                max="1000"
            /> meters</label>
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
      </div>

      <!-- OpenStreetMap container -->
      <div id="map" style="height: 500px;"></div> <!-- Map container -->
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";
import { checkUserLocation, fetchNearbyUsers } from '@/services/location-service';
import { ElNotification } from "element-plus";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

export default {
  components: {
    LoveBellSidebar,
  },
  data() {
    return {
      range: 500, // Distance range
      users: [], // Store user positions
      isScanning: false, // Controls scanning animation
      userId: localStorage.getItem('userId'),
      map: null, // The Leaflet map object
    };
  },
  mounted() {
    this.checkUserLocation();
    this.initMap(); // Initialize the map when the component is mounted
  },
  methods: {
    // Initialize the map
    initMap() {
      // Create the map centered at a default location (change if needed)
      this.map = L.map("map").setView([10.762622, 106.660172], 13); // Center at HCM city

      // Add OpenStreetMap tiles
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(this.map);
    },

    // Start scanning for nearby users and display them on the map
    async startScanning() {
      this.isScanning = true; // Enable scanning animation

      // Fetch nearby users
      const nearbyUsers = await fetchNearbyUsers(this.userId, this.range);
      if (nearbyUsers?.data) {
        this.isScanning = false;
        ElNotification({
          title: 'Success',
          message: 'Fetch Nearby Users Successfully.',
          type: 'success',
        });
        // Clear existing markers
        this.clearMap();

        // Add users to the map
        nearbyUsers.data.forEach((user) => {
          const { latitude, longitude, name } = user;

          // Create marker for each user position
          const marker = L.marker([latitude, longitude]).addTo(this.map);
          marker.bindPopup(`<b>${name || "Anonymous"}</b>`).openPopup();
        });

        this.users = nearbyUsers.data; // Update users list
      }
    },

    // Clear all markers from the map
    clearMap() {
      this.map.eachLayer((layer) => {
        if (layer instanceof L.Marker) {
          this.map.removeLayer(layer);
        }
      });
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
  flex-direction: column;
  flex: 1;
  padding-left: 20px;
}

.page-title {
  display: flex;
  align-items: center;
}

.page-title h2 {
  font-size: 35px;
  color: #ff33cc;
}

hr {
  border: none;
  height: 2px;
  background-color: #ff66ff;
  margin: 20px 0;
}

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
</style>
