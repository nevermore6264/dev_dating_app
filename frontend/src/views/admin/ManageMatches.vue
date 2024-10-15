<template>
  <div>
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <h1>Manage Matches</h1>

      <!-- Search bar -->
      <el-input
          v-model="searchQuery"
          placeholder="Search by User1 ID or User2 ID"
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
        <th>User 1 ID</th>
        <th>User 2 ID</th>
        <th>Created At</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="match in filteredMatches" :key="match.match_id">
        <td>{{ match.match_id }}</td>
        <td>{{ match.user1_id }}</td>
        <td>{{ match.user2_id }}</td>
        <td>{{ match.created_at }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { ElMessage } from 'element-plus';
import { getAllMatches } from '@/services/admin/admin-match-service';
import { Search } from "@element-plus/icons-vue";

const matches = ref([]);
const searchQuery = ref('');

// Filter matches based on search query
const filteredMatches = computed(() => {
  const query = searchQuery.value.toLowerCase();
  return matches.value.filter(match =>
      match.user1_id.toString().includes(query) ||
      match.user2_id.toString().includes(query)
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
</script>
