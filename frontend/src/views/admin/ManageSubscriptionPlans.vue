<template>
  <div>
    <h1>Manage SubscriptionPlans</h1>

    <!-- Table for displaying SubscriptionPlans -->
    <table border="1" style="width: 100%; border-collapse: collapse;">
      <thead>
      <tr>
        <th>ID</th>
        <th>SubscriptionPlan Name</th>
        <th>Description</th>
        <th>Swipe Limit</th>
        <th>Duration (Days)</th>
        <th style="width: 120px">Operations</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="sp in subscriptionPlans" :key="sp.planId">
        <td>{{ sp.planId }}</td>
        <td>{{ sp.name }}</td>
        <td>{{ sp.description || '-' }}</td>
        <td>{{ sp.swipeLimit != null ? sp.swipeLimit : '-' }}</td>
        <td>{{ sp.durationDays != null ? sp.durationDays : '-' }}</td>
        <td>
          <el-button type="primary" @click="openDetailModal(sp)">View Details</el-button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Modal for viewing subscriptionPlan details -->
    <el-dialog v-model="isDetailModalVisible" title="SubscriptionPlan Details" width="30%">
      <div>
        <el-form label-position="top" :model="selectedSubscriptionPlan">
          <el-form-item label="ID">
            <el-input v-model="selectedSubscriptionPlan.planId" disabled></el-input>
          </el-form-item>
          <el-form-item label="SubscriptionPlan Name">
            <el-input v-model="selectedSubscriptionPlan.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="Description">
            <el-input :rows="2" type="textarea"
                      v-model="selectedSubscriptionPlan.description" disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Swipe Limit">
            <el-input v-model="selectedSubscriptionPlan.swipeLimit" :value="selectedSubscriptionPlan.swipeLimit != null ? selectedSubscriptionPlan.swipeLimit : '-'" disabled></el-input>
          </el-form-item>
          <el-form-item label="Duration (Days)">
            <el-input v-model="selectedSubscriptionPlan.durationDays" :value="selectedSubscriptionPlan.durationDays != null ? selectedSubscriptionPlan.durationDays : '-'" disabled></el-input>
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

// Variables for holding subscriptionPlans and modal state
const subscriptionPlans = ref([]);
const isDetailModalVisible = ref(false);
const selectedSubscriptionPlan = ref({});

// Fetch subscriptionPlans when the component is mounted
onMounted(async () => {
  await fetchSubscriptionPlans();
});

// Fetch all subscriptionPlans from the server
const fetchSubscriptionPlans = async () => {
  try {
    subscriptionPlans.value = await getAllSubscriptionPlans();
  } catch (error) {
    ElMessage.error('Failed to fetch subscriptionPlans. Please try again later.');
  }
};

// Function to open the detail modal and set selected subscriptionPlan data
const openDetailModal = (sp) => {
  selectedSubscriptionPlan.value = sp;
  isDetailModalVisible.value = true;
};
</script>
