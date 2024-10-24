<template>
  <div>
    <h1>Manage Packages</h1>

    <!-- Table for displaying packages -->
    <table border="1" style="width: 100%; border-collapse: collapse;">
      <thead>
      <tr>
        <th>ID</th>
        <th>Package Name</th>
        <th>Description</th>
        <th>Swipe Limit</th>
        <th>Duration (Days)</th>
        <th style="width: 120px">Operations</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="pkg in packages" :key="pkg.packageId">
        <td>{{ pkg.packageId }}</td>
        <td>{{ pkg.name }}</td>
        <td>{{ pkg.description || '-' }}</td>
        <td>{{ pkg.swipeLimit != null ? pkg.swipeLimit : '-' }}</td>
        <td>{{ pkg.durationDays != null ? pkg.durationDays : '-' }}</td>
        <td>
          <el-button type="primary" @click="openDetailModal(pkg)">View Details</el-button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Modal for viewing package details -->
    <el-dialog v-model="isDetailModalVisible" title="Package Details" width="30%">
      <div>
        <el-form label-position="top" :model="selectedPackage">
          <el-form-item label="ID">
            <el-input v-model="selectedPackage.packageId" disabled></el-input>
          </el-form-item>
          <el-form-item label="Package Name">
            <el-input v-model="selectedPackage.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="Description">
            <el-input :rows="2" type="textarea"
                      v-model="selectedPackage.description" disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Swipe Limit">
            <el-input v-model="selectedPackage.swipeLimit" :value="selectedPackage.swipeLimit != null ? selectedPackage.swipeLimit : '-'" disabled></el-input>
          </el-form-item>
          <el-form-item label="Duration (Days)">
            <el-input v-model="selectedPackage.durationDays" :value="selectedPackage.durationDays != null ? selectedPackage.durationDays : '-'" disabled></el-input>
          </el-form-item>
        </el-form>
      </div>

      <span class="dialog-footer">
        <el-button @click="isDetailModalVisible = false">Close</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllPackages } from '@/services/admin/admin-package-service';
import { ElMessage } from 'element-plus';

// Variables for holding packages and modal state
const packages = ref([]);
const isDetailModalVisible = ref(false);
const selectedPackage = ref({});

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

// Function to open the detail modal and set selected package data
const openDetailModal = (pkg) => {
  selectedPackage.value = pkg;
  isDetailModalVisible.value = true;
};
</script>
