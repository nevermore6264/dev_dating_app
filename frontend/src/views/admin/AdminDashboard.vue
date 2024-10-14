<template>
  <div class="admin-dashboard">
    <aside class="sidebar">
      <div class="logo-container">
        <img src="@/assets/love-bell.png" alt="Logo" class="logo" />
        <span class="logo-text">LOVE BELL</span>
      </div>
      <nav>
        <ul>
          <li @click="navigateTo('Home')">
            <el-icon><House /></el-icon> Home
          </li>
          <li @click="navigateTo('Users')">
            <el-icon><User /></el-icon> Users
          </li>
          <li @click="navigateTo('Settings')">
            <el-icon><Setting /></el-icon> Settings
          </li>
          <li @click="navigateTo('Cafes')">
            <el-icon><CoffeeCup /></el-icon> Manage Cafes
          </li>
        </ul>
      </nav>
    </aside>

    <main class="main-content">
      <router-view /> <!-- Dùng router-view để hiển thị các thành phần con -->
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { House, User, Setting } from '@element-plus/icons-vue'; // Nhập các icon cần thiết

const router = useRouter();

const navigateTo = (component) => {
  // Điều hướng đến trang tương ứng với tên thành phần
  router.push(`/admin/${component.toLowerCase()}`);
};
</script>

<style>
.admin-dashboard {
  display: flex;
  height: calc(100vh - 56px);
}

.sidebar {
  width: 200px;
  background-color: #304156;
  padding: 20px;
  border-right: 1px solid #ddd;
}

.logo-container {
  display: flex;
  align-items: center; /* Căn giữa logo và văn bản theo chiều dọc */
  margin-bottom: 20px; /* Khoảng cách dưới logo */
}

.logo {
  width: 40px; /* Chiều rộng logo */
  height: auto; /* Tự động điều chỉnh chiều cao */
  margin-right: 10px; /* Khoảng cách giữa logo và văn bản */
}

.logo-text {
  font-size: 1.2em; /* Kích thước chữ của văn bản */
  font-weight: bold; /* Đậm chữ */
  color: #ffffff;
}

.sidebar nav ul {
  list-style: none;
  padding: 0;
}

.sidebar nav li {
  cursor: pointer;
  margin: 10px 0;
  color: #ffffff;
  display: flex; /* Thay đổi để hiển thị icon và văn bản cạnh nhau */
  align-items: center; /* Căn giữa icon và văn bản */
}

.sidebar el-icon {
  padding-right: 15px !important; /* Tăng khoảng cách giữa icon và văn bản */
}

.sidebar nav li:hover {
  text-decoration: underline;
}

.main-content {
  flex: 1;
  padding: 20px;
}
</style>