<template>
  <div class="admin-dashboard">
    <aside class="sidebar">
      <h2>Admin Dashboard</h2>
      <nav>
        <ul>
          <li @click="navigateTo('Home')">Home</li>
          <li @click="navigateTo('Users')">Users</li>
          <li @click="navigateTo('Settings')">Settings</li>
        </ul>
      </nav>
    </aside>

    <main class="main-content">
      <component :is="currentComponent" />
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const currentComponent = ref('Home'); // Default component
const router = useRouter();

const navigateTo = (component) => {
  currentComponent.value = component;
  // Use Vue Router to navigate if necessary
  router.push(`/admin/${component.toLowerCase()}`);
};
</script>

<style>
.admin-dashboard {
  display: flex; /* Use flexbox for layout */
}

.sidebar {
  width: 200px; /* Set width for sidebar */
  background-color: #f8f9fa; /* Light background color */
  padding: 20px; /* Add some padding */
  border-right: 1px solid #ddd; /* Right border for separation */
}

.sidebar h2 {
  margin-bottom: 20px; /* Space below the title */
}

.sidebar nav ul {
  list-style: none; /* Remove default list styles */
  padding: 0; /* Remove padding */
}

.sidebar nav li {
  cursor: pointer; /* Change cursor to pointer */
  margin: 10px 0; /* Space between items */
}

.sidebar nav li:hover {
  text-decoration: underline; /* Underline on hover */
}

.main-content {
  flex: 1; /* Allow main content to fill the remaining space */
  padding: 20px; /* Add some padding */
}
</style>
