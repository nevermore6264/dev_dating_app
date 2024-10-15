<template>
  <div>
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <h1>Manage Users</h1>

      <!-- Search bar -->
      <el-input
          v-model="searchQuery"
          placeholder="Search by Email, Phone"
          style="width: 300px;"
          @input="filterUsers"
      >
        <template #prepend>
          <el-button :icon="Search" />
        </template>
      </el-input>
    </div>

    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Status</th>
        <th>Role</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in filteredUsers" :key="user.userId">
        <td>{{ user.userId }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.phone ? user.phone : 'Not set' }}</td>
        <td>{{ user.status }}</td>
        <td>{{ user?.role?.roleName }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { ElMessage, ElNotification } from 'element-plus';
import {
  getAllMatches
} from '@/services/admin/admin-match-service';
import {Search} from "@element-plus/icons-vue";

const users = ref([]);
const searchQuery = ref('');

// Lọc danh sách người dùng dựa trên tìm kiếm
const filteredUsers = computed(() => {
  const query = searchQuery.value.toLowerCase();
  return users.value.filter(user =>
      user.email.toLowerCase().includes(query) ||
      user.phone?.toLowerCase().includes(query)
  );
});

// Load users when component is mounted
onMounted(async () => {
  await fetchUsers();
});

// Fetch users
const fetchUsers = async () => {
  try {
    users.value = await getAllMatches();
  } catch (error) {
    ElMessage.error('Failed to fetch users. Please try again later.');
  }
};
</script>
