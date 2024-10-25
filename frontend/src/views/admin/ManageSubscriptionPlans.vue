<template>
  <div>
    <h1>Manage Subscription Plans</h1>

    <!-- Table for displaying SubscriptionPlans -->
    <table border="1" style="width: 100%; border-collapse: collapse;">
      <thead>
      <tr>
        <th>ID</th>
        <th>Subscription Plan Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Duration (Days)</th>
        <th>Image</th>
        <th style="width: 120px">Operations</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="sp in subscriptionPlans" :key="sp.planId">
        <td>{{ sp.planId }}</td>
        <td>{{ sp.name }}</td>
        <td>{{ sp.description || '-' }}</td>
        <td>{{ sp.price != null ? `$${sp.price}` : '-' }}</td>
        <td>{{ sp.duration != null ? sp.duration : '-' }}</td>
        <td><img :src="sp.imageUrl" alt="Plan Image" width="50" /></td>
        <td>
          <el-button type="primary" @click="openDetailModal(sp)">View Details</el-button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Modal for viewing subscriptionPlan details -->
    <el-dialog v-model="isDetailModalVisible" title="Subscription Plan Details" width="30%">
      <div>
        <el-form label-position="top" :model="selectedSubscriptionPlan">
          <el-form-item label="ID">
            <el-input v-model="selectedSubscriptionPlan.planId" disabled></el-input>
          </el-form-item>
          <el-form-item label="Subscription Plan Name">
            <el-input v-model="selectedSubscriptionPlan.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="Description">
            <el-input :rows="2" type="textarea" v-model="selectedSubscriptionPlan.description" disabled></el-input>
          </el-form-item>
          <el-form-item label="Price">
            <el-input v-model="selectedSubscriptionPlan.price" :value="selectedSubscriptionPlan.price != null ? `$${selectedSubscriptionPlan.price}` : '-'" disabled></el-input>
          </el-form-item>
          <el-form-item label="Duration (Days)">
            <el-input v-model="selectedSubscriptionPlan.duration" :value="selectedSubscriptionPlan.duration != null ? selectedSubscriptionPlan.duration : '-'" disabled></el-input>
          </el-form-item>
          <el-form-item label="Like Limit">
            <el-input v-model="selectedSubscriptionPlan.hasLikeLimit" :value="selectedSubscriptionPlan.hasLikeLimit ? 'Yes' : 'No'" disabled></el-input>
          </el-form-item>
          <el-form-item label="Watch Like">
            <el-input v-model="selectedSubscriptionPlan.hasWatchLike" :value="selectedSubscriptionPlan.hasWatchLike ? 'Yes' : 'No'" disabled></el-input>
          </el-form-item>
          <el-form-item label="Show Priority">
            <el-input v-model="selectedSubscriptionPlan.hasShowPriority" :value="selectedSubscriptionPlan.hasShowPriority ? 'Yes' : 'No'" disabled></el-input>
          </el-form-item>
          <el-form-item label="View Profile">
            <el-input v-model="selectedSubscriptionPlan.hasViewProfile" :value="selectedSubscriptionPlan.hasViewProfile ? 'Yes' : 'No'" disabled></el-input>
          </el-form-item>
          <el-form-item label="Image">
            <img :src="selectedSubscriptionPlan.imageUrl" alt="Plan Image" width="100" />
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
import { getAllSubscriptionPlans } from '@/services/admin/admin-subscriptionPlan-service';
import { ElMessage } from 'element-plus';

// Variables for holding subscription plans and modal state
const subscriptionPlans = ref([]);
const isDetailModalVisible = ref(false);
const selectedSubscriptionPlan = ref({});

// Fetch subscription plans when the component is mounted
onMounted(async () => {
  await fetchSubscriptionPlans();
});

// Fetch all subscription plans from the server
const fetchSubscriptionPlans = async () => {
  try {
    const response = await getAllSubscriptionPlans();
    subscriptionPlans.value = response;
  } catch (error) {
    ElMessage.error('Failed to fetch subscription plans. Please try again later.');
  }
};

// Function to open the detail modal and set selected subscription plan data
const openDetailModal = (sp) => {
  selectedSubscriptionPlan.value = { ...sp };
  isDetailModalVisible.value = true;
};
</script>
