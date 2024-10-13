<template>
  <div class="admin-dashboard">
    <aside class="sidebar">
      <h2>Bảng điều khiển Quản trị</h2>
      <nav>
        <ul>
          <li @click="navigateTo('Home')">Home</li>
          <li @click="navigateTo('Users')">Users</li>
          <li @click="navigateTo('Settings')">Settings</li>
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

const router = useRouter();

const navigateTo = (component) => {
  // Điều hướng đến trang tương ứng với tên thành phần
  router.push(`/admin/${component.toLowerCase()}`);
};
</script>

<style>
.admin-dashboard {
  display: flex;
}

.sidebar {
  width: 200px;
  background-color: #f8f9fa;
  padding: 20px;
  border-right: 1px solid #ddd;
}

.sidebar h2 {
  margin-bottom: 20px;
}

.sidebar nav ul {
  list-style: none;
  padding: 0;
}

.sidebar nav li {
  cursor: pointer;
  margin: 10px 0;
}

.sidebar nav li:hover {
  text-decoration: underline;
}

.main-content {
  flex: 1;
  padding: 20px;
}
</style>
