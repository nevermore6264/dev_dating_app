<template>
  <div>
    <h1>Manage Packages</h1>

    <!-- Table for displaying packages -->
    <table style="width: 100%; border-collapse: collapse;">
      <thead>
      <tr>
        <th>ID</th>
        <th>Package Name</th>
        <th>Description</th>
        <th>Swipe Limit</th>
        <th>Duration (Days)</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="pkg in packages" :key="pkg.packageId">
        <td>{{ pkg.packageId }}</td>
        <td>{{ pkg.name }}</td>
        <td>{{ pkg.description || '-' }}</td> <!-- Hiển thị "-" nếu không có mô tả -->
        <td>{{ pkg.swipeLimit != null ? pkg.swipeLimit : '-' }}</td> <!-- Hiển thị "-" nếu không có giá trị -->
        <td>{{ pkg.durationDays != null ? pkg.durationDays : '-' }}</td> <!-- Hiển thị "-" nếu không có giá trị -->
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllPackages } from '@/services/admin/admin-package-service';
import { ElMessage } from 'element-plus';

const packages = ref([]);

// Fetch packages when the component is mounted
onMounted(async () => {
  await fetchPackages();
});

// Fetch all packages from the server
const fetchPackages = async () => {
  try {
    packages.value = await getAllPackages();
  } catch (error) {
    ElMessage.error('Failed to fetch packages. Please try again later.');
  }
};

// Formatter function to display "-" if value is null or undefined
const formatValue = (row, column, cellValue) => {
  return cellValue == null ? '-' : cellValue;
};
</script>
