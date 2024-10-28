<template>
  <div>
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <h1>Manage Matches</h1>

      <!-- Search bar -->
      <el-input
          v-model="searchQuery"
          placeholder="Search by User1 Email or User2 Email"
          style="width: 300px;"
          @input="filterMatches"
      >
        <template #prepend>
          <el-button :icon="Search" />
        </template>
      </el-input>
    </div>

    <table>
      <thead>
      <tr>
        <th>Match ID</th>
        <th>User 1</th>
        <th>User 1 Location</th>
        <th>User 2</th>
        <th>User 2 Location</th>
        <th>Created At</th>
      </tr>
      </thead>
      <tbody>
        <tr
            v-for="match in filteredMatches"
            :key="match.matchId"
            style="cursor: pointer;"
        >
          <td>{{ match.matchId }}</td>
          <td>
            <a @click.stop="fetchUserDetails(match.user1Id)" class="clickable">
              {{ match.user1Email }}
            </a>
            <span v-if="match.user1Name"> - {{ match.user1Name }}</span>
          </td>
          <td>{{ match.user1Location }}</td>
          <td>
            <a @click.stop="fetchUserDetails(match.user2Id)" class="clickable">
              {{ match.user2Email }}
            </a>
            <span v-if="match.user2Name"> - {{ match.user2Name }}</span>
          </td>
          <td>{{ match.user2Location }}</td>
          <td>{{ formatSubmissionDate(match.createdAt) }}</td>
        </tr>
      </tbody>
    </table>

    <!-- User Details Dialog -->
    <el-dialog v-model="isDialogVisible" width="50%" title="User Details">
      <div v-if="selectedUser">
        <el-form label-position="top">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="ID">
                <el-input v-model="selectedUser.userId" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Email">
                <el-input v-model="selectedUser.email" disabled />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Name">
                <el-input v-model="selectedUser.name" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Address">
                <el-input v-model="selectedUser.address" disabled />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Status">
                <el-switch v-model="selectedUser.status"
                           active-value="ACTIVE"
                           inactive-value="INACTIVE"
                           disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Role">
                <el-input v-model="selectedUser.roleName" disabled />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Bio">
                <el-input v-model="selectedUser.bio" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Gender">
                <el-input v-model="selectedUser.gender" disabled />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Phone">
                <el-input v-model="selectedUser.phone" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Package Name">
                <el-input v-model="selectedUser.packageName" disabled />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="Photos">
            <el-row :gutter="20">
              <el-col v-for="url in selectedUser.photoUrls" :key="url" :span="6">
                <el-image
                    :src="url"
                    fit="cover"
                    :preview-src-list="selectedUser.photoUrls"
                    lazy
                />
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button @click="isDialogVisible = false">Close</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref, computed} from 'vue';
import {ElMessage} from 'element-plus';
import {getAllMatches} from '@/services/admin/admin-match-service'; // Update with actual service paths
import {getUserById} from '@/services/admin/admin-user-service'; // Update with actual service paths
import {Search} from "@element-plus/icons-vue";
import {format} from "date-fns";

const matches = ref([]);
const searchQuery = ref('');
const isDialogVisible = ref(false);
const selectedUser = ref(null);

// Filter matches based on search query
const filteredMatches = computed(() => {
  const query = searchQuery.value.toLowerCase();
  return matches.value.filter(match =>
      match.user1Email.toString().includes(query) ||
      match.user2Email.toString().includes(query)
  );
});

// Fetch matches when component is mounted
onMounted(async () => {
  await fetchMatches();
});

// Fetch all matches
const fetchMatches = async () => {
  try {
    matches.value = await getAllMatches();
  } catch (error) {
    ElMessage.error('Failed to fetch matches. Please try again later.');
  }
};

// Fetch user details on email click
const fetchUserDetails = async (id) => {
  try {
    selectedUser.value = await getUserById(id); // Fetch user details based on email
    isDialogVisible.value = true;
  } catch (error) {
    console.log(error)
    ElMessage.error('Failed to fetch user details.');
  }
};

// Format the date
const formatSubmissionDate = (dateString) => {
  if (!dateString) return '-'; // Handle null or undefined dates
  return format(new Date(dateString), 'MMMM dd, yyyy HH:mm'); // Format: October 15, 2024 14:30
};
</script>

<style scoped>
a.clickable {
  color: #3498db !important; /* Initial color for the link */
  text-decoration: none !important; /* Remove underline */
  transition: color 0.3s ease !important; /* Smooth transition for hover effect */
}

a.clickable:hover {
  color: #2ecc71 !important; /* Change color on hover */
  text-decoration: underline !important; /* Underline on hover */
}
</style>
