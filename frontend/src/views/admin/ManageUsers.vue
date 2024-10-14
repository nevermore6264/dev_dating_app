<template>
  <div>
    <h1>Manage Users</h1>

    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Status</th>
        <th>Role</th>
        <th style="width: 120px">Operations</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.userId">
        <td>{{ user.userId }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.phone ? user.phone : 'Not set' }}</td>
        <td>{{ user.status }}</td>
        <td>{{ user?.role?.roleName }}</td>
        <td>
          <el-button type="danger" @click="lockOrUnLockUser(user.userId)">
            {{ user.status === 'INACTIVE' ? 'Activate' : 'Deactivate' }}
          </el-button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { ElMessage, ElNotification } from 'element-plus';
import {
  getAllUsers,
  lockOrUnLockUser as lockOrUnLockUserAPI
} from '@/services/admin/admin-user-service';
const users = ref([]);

// Load users when component is mounted
onMounted(async () => {
  await fetchUsers();
});

// Fetch users
const fetchUsers = async () => {
  try {
    users.value = await getAllUsers();
  } catch (error) {
    ElMessage.error('Failed to fetch users. Please try again later.');
  }
};

// Toggle user status (Activate/Deactivate)
const lockOrUnLockUser = async (id) => {
  const action = users.value.find(user => user.userId === id).status === 'INACTIVE' ? 'Activate' : 'Deactivate';
  if (confirm(`Are you sure you want to ${action} this user?`)) {
    try {
      await lockOrUnLockUserAPI(id);
      await fetchUsers();
      ElNotification({
        title: 'Success',
        message: `User successfully ${action}d.`,
        type: 'success',
      });
    } catch (error) {
      ElNotification({
        title: 'Error',
        message: error?.message,
        type: 'error',
      });
    }
  }
};
</script>
