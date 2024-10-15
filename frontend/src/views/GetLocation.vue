<template>
  <div class="emergency-page-container">
    <!-- Sidebar -->
    <LoveBellSidebar />

    <!-- Nội dung chính của trang -->
    <div class="emergency-page">
      <!-- Tiêu đề trang -->
      <div class="header">
        <h1>GET CURRENT LOCATION</h1>
        <button class="emergency-button">Get Location</button>
      </div>
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";

export default {
  components: {
    LoveBellSidebar,
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
