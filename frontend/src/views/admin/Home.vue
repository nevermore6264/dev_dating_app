<template>
  <div class="admin-home">
    <el-card shadow="hover" class="dashboard-card">
      <h2>Welcome to the Admin Dashboard!</h2>

      <div class="stats-grid">
        <el-card class="stat-card" shadow="hover" @click="navigateTo('/admin/users')"> <!-- Navigate to users -->
          <el-icon class="stat-icon" color="#f56c6c">
            <User />
          </el-icon>
          <div class="stat-content">
            <h3>Users</h3>
            <p>{{ userCount }} users</p>
          </div>
        </el-card>

        <el-card class="stat-card" shadow="hover" @click="navigateTo('/admin/cafes')"> <!-- Navigate to cafes -->
          <el-icon class="stat-icon" color="#67c23a">
            <HomeFilled />
          </el-icon>
          <div class="stat-content">
            <h3>Cafes</h3>
            <p>{{ cafeCount }} cafes</p>
          </div>
        </el-card>

        <el-card class="stat-card" shadow="hover" @click="navigateTo('/admin/contacts')"> <!-- Navigate to contacts -->
          <el-icon class="stat-icon" color="#e6a23c">
            <ChatLineRound />
          </el-icon>
          <div class="stat-content">
            <h3>Contacts</h3>
            <p>{{ contactCount }} contacts</p>
          </div>
        </el-card>
      </div>

      <!-- Chart Section -->
      <div id="statistics-chart" class="chart-container"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { User, HomeFilled, ChatLineRound } from '@element-plus/icons-vue';
import { useRouter } from 'vue-router';
import { getAllCafes } from '@/services/admin/admin-cafe-service';
import { getAllContactForms } from '@/services/admin/admin-contact-service';
import { getAllUsers } from '@/services/admin/admin-user-service';
import * as echarts from 'echarts'; // Import ECharts

// State variables
const userCount = ref(0);
const cafeCount = ref(0);
const contactCount = ref(0);

const router = useRouter();

// Function to fetch statistics
const fetchStats = async () => {
  try {
    const users = await getAllUsers();
    userCount.value = users.length;
    const contacts = await getAllContactForms();
    contactCount.value = contacts.length;
    const cafes = await getAllCafes();
    cafeCount.value = cafes.length;
    renderChart(); // Render the chart after fetching data
  } catch (error) {
    console.error('Error fetching statistics:', error.message);
  }
};

// Function to navigate to different pages
const navigateTo = (path) => {
  router.push(path); // Use router to navigate
};

// Function to render the chart
const renderChart = () => {
  const chartDom = document.getElementById('statistics-chart');
  const myChart = echarts.init(chartDom);
  const option = {
    title: {
      text: 'Statistics Overview'
    },
    tooltip: {},
    legend: {
      data: ['Users', 'Cafes', 'Contacts']
    },
    xAxis: {
      data: ['Users', 'Cafes', 'Contacts']
    },
    yAxis: {},
    series: [
      {
        name: 'Count',
        type: 'bar',
        data: [userCount.value, cafeCount.value, contactCount.value]
      }
    ]
  };
  option && myChart.setOption(option);
};

// Fetch stats and render chart on component mount
onMounted(() => {
  fetchStats();
});
</script>

<style scoped>
.admin-home {
  padding: 30px;
  background: linear-gradient(135deg, #f0f4f8, #d9e2ec);
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column; /* Center the content vertically */
}

.dashboard-card {
  background-color: #fff;
  border-radius: 16px;
  padding: 40px;
  max-width: 900px;
  text-align: center;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  margin-bottom: 40px; /* Space for the chart */
}

h2 {
  font-size: 2.4em;
  color: #304156;
  margin-bottom: 20px;
}

p {
  font-size: 1.1em;
  color: #6b7280;
  margin-bottom: 30px;
}

.stats-grid {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.stat-card {
  align-items: center;
  padding: 20px;
  border-radius: 12px;
  background: linear-gradient(135deg, #fef5f5, #fff);
  transition: transform 0.3s ease;
  min-width: 250px;
  cursor: pointer; /* Add pointer cursor */
}

.stat-card:hover {
  transform: translateY(-10px);
}

.stat-icon {
  font-size: 3em;
  margin-right: 0;
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

.chart-container {
  width: 100%;
  height: 400px;
  margin-top: 30px; /* Add margin for the chart */
}
</style>
