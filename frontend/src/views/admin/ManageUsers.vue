<template>
  <div>
    <h1>Manage Users</h1>

    <!-- Button to open modal for adding a new user -->
    <el-button type="success" @click="showAddModal">Add New User</el-button>

    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>User Name</th>
        <th>Email</th>
        <th>Status</th>
        <th style="width: 170px">Operations</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.userId">
        <td>{{ user.userId }}</td>
        <td>{{ user.username }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.status }}</td>
        <td>
          <el-button type="warning" @click="setUserToEdit(user)">Edit</el-button>
          <el-button type="danger" @click="toggleUserStatus(user.userId)">
            {{ user.status === 'INACTIVE' ? 'Activate' : 'Deactivate' }}
          </el-button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Dialog for adding a new user -->
    <el-dialog v-model="isAddModalVisible" title="Add New User">
      <div>
        <el-form label-position="top">
          <!-- User Name -->
          <el-form-item label="User Name (*)">
            <el-input v-model="newUser.username" placeholder="Enter user name"></el-input>
          </el-form-item>

          <!-- Email -->
          <el-form-item label="Email (*)">
            <el-input v-model="newUser.email" placeholder="Enter email" type="email"></el-input>
          </el-form-item>

          <!-- Password -->
          <el-form-item label="Password (*)">
            <el-input v-model="newUser.password" placeholder="Enter password" type="password"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <!-- Footer Buttons -->
      <span class="dialog-footer">
        <el-button type="success" @click="addUser">Add</el-button>
        <el-button type="danger" @click="isAddModalVisible = false">Cancel</el-button>
      </span>
    </el-dialog>

    <!-- Dialog for updating user details -->
    <el-dialog v-model="isEditModalVisible" title="Edit User">
      <div>
        <el-form label-position="top">
          <!-- User Name -->
          <el-form-item label="User Name (*)">
            <el-input v-model="userToEdit.username" placeholder="Enter user name"></el-input>
          </el-form-item>

          <!-- Email -->
          <el-form-item label="Email (*)">
            <el-input v-model="userToEdit.email" placeholder="Enter email" type="email"></el-input>
          </el-form-item>

          <!-- Status -->
          <el-form-item label="Status">
            <el-select v-model="userToEdit.status">
              <el-option label="Active" value="ACTIVE"></el-option>
              <el-option label="Inactive" value="INACTIVE"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button type="primary" @click="updateUserDetails">Edit</el-button>
        <el-button @click="isEditModalVisible = false">Cancel</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { ElMessage, ElNotification } from 'element-plus';
import {
  createUser,
  getAllUsers,
  toggleUserStatus as toggleUserStatusAPI,
  updateUser as updateUserAPI,
} from '@/services/admin/admin-user-service';

const users = ref([]);
const newUser = ref({
  username: '',
  email: '',
  password: '',
});
const userToEdit = ref({
  username: '',
  email: '',
  status: '',
});

const isAddModalVisible = ref(false);
const isEditModalVisible = ref(false);

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

// Add new user
const addUser = async () => {
  if (!newUser.value.username || !newUser.value.email || !newUser.value.password) {
    ElNotification({
      title: 'Error',
      message: 'Please enter complete information.',
      type: 'error',
    });
    return;
  }
  try {
    await createUser(newUser.value);
    newUser.value = { username: '', email: '', password: '' };
    isAddModalVisible.value = false;
    await fetchUsers();
    ElNotification({
      title: 'Success',
      message: 'New user added successfully.',
      type: 'success',
    });
  } catch (error) {
    ElNotification({
      title: 'Error',
      message: error?.message,
      type: 'error',
    });
  }
};

// Set user to edit
const setUserToEdit = (user) => {
  userToEdit.value = { ...user };
  isEditModalVisible.value = true;
};

// Update user details
const updateUserDetails = async () => {
  if (!userToEdit.value.username || !userToEdit.value.email) {
    ElMessage.error('Please enter complete information.');
    return;
  }
  try {
    await updateUserAPI(userToEdit.value.userId, userToEdit.value);
    isEditModalVisible.value = false;
    await fetchUsers();
    ElNotification({
      title: 'Success',
      message: 'User details updated successfully.',
      type: 'success',
    });
  } catch (error) {
    ElNotification({
      title: 'Error',
      message: error?.message,
      type: 'error',
    });
  }
};

// Toggle user status (Activate/Deactivate)
const toggleUserStatus = async (id) => {
  const action = users.value.find(user => user.userId === id).status === 'INACTIVE' ? 'Activate' : 'Deactivate';
  if (confirm(`Are you sure you want to ${action} this user?`)) {
    try {
      await toggleUserStatusAPI(id);
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

const showAddModal = () => {
  isAddModalVisible.value = true;
};
</script>
