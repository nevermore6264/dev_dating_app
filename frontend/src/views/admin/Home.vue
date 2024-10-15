<template>
  <div class="admin-home">
    <el-card shadow="hover" class="dashboard-card">
      <h2>Welcome to the Admin Dashboard!</h2>
      <p>Manage users, cafes, and contacts easily using the navigation menu on the left.</p>

      <div class="stats-grid">
        <el-card class="stat-card" shadow="hover">
          <el-icon class="stat-icon" color="#f56c6c">
            <User />
          </el-icon>
          <div class="stat-content">
            <h3>Users</h3>
            <p>Manage and view all users</p>
          </div>
        </el-card>

        <el-card class="stat-card" shadow="hover">
          <el-icon class="stat-icon" color="#67c23a">
            <HomeFilled />
          </el-icon>
          <div class="stat-content">
            <h3>Cafes</h3>
            <p>Manage your cafes efficiently</p>
          </div>
        </el-card>

        <el-card class="stat-card" shadow="hover">
          <el-icon class="stat-icon" color="#e6a23c">
            <ChatLineRound />
          </el-icon>
          <div class="stat-content">
            <h3>Contacts</h3>
            <p>Respond to customer inquiries</p>
          </div>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { User, HomeFilled, ChatLineRound } from '@element-plus/icons-vue';
</script>

<style scoped>
.admin-home {
  padding: 30px;
  background: linear-gradient(135deg, #f0f4f8, #d9e2ec); /* Subtle gradient background */
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.dashboard-card {
  background-color: #fff;
  border-radius: 16px;
  padding: 40px;
  max-width: 900px;
  text-align: center;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1); /* Subtle shadow */
}

h2 {
  font-size: 2.4em;
  color: #304156; /* Darker title color */
  margin-bottom: 20px;
}

p {
  font-size: 1.1em;
  color: #6b7280;
  margin-bottom: 30px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
  border-radius: 12px;
  background: linear-gradient(135deg, #fef5f5, #fff);
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-10px);
}

.stat-icon {
  font-size: 3em;
  margin-right: 20px;
}

.stat-content h3 {
  font-size: 1.8em;
  color: #2c3e50;
  margin-bottom: 5px;
}

.stat-content p {
  font-size: 1em;
  color: #7f8c8d;
}
</style>
