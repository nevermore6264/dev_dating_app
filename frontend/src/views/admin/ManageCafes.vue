<template>
  <div>
    <h1>Quản Lý Quán Cafe</h1>

    <h2>Tạo Quán Cafe</h2>
    <input v-model="newCafeName" placeholder="Nhập tên quán cafe" />
    <button @click="addCafe">Thêm Quán Cafe</button>

    <h2>Các Quán Cafe</h2>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Tên Quán Cafe</th>
        <th>Thao Tác</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="cafe in cafes" :key="cafe.id">
        <td>{{ cafe.id }}</td>
        <td>{{ cafe.name }}</td>
        <td>
          <button @click="setCafeToEdit(cafe)">Sửa</button>
          <button @click="removeCafe(cafe.id)">Xóa</button>
        </td>
      </tr>
      </tbody>
    </table>

    <h2>Cập Nhật Quán Cafe</h2>
    <input v-model="cafeToEdit.name" placeholder="Tên quán cafe" />
    <button @click="updateCafeDetails">Cập Nhật</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllCafes, createCafe, updateCafe as updateCafeAPI, deleteCafe as deleteCafeAPI } from '@/services/admin/admin-cafe-service';

const cafes = ref([]);
const newCafeName = ref('');
const cafeToEdit = ref({});

// Automatically fetch cafes when component is mounted
onMounted(async () => {
  await fetchCafes();
});

// Function to fetch cafes
const fetchCafes = async () => {
  try {
    cafes.value = await getAllCafes();
  } catch (error) {
    console.error(error);
  }
};

// Function to add a new cafe
const addCafe = async () => {
  if (!newCafeName.value) {
    alert('Vui lòng nhập tên quán cafe.');
    return;
  }
  try {
    const cafeRequest = { name: newCafeName.value };
    await createCafe(cafeRequest);
    newCafeName.value = ''; // Reset input
    await fetchCafes(); // Reload the cafe list
  } catch (error) {
    console.error(error);
  }
};

// Function to set cafe to edit
const setCafeToEdit = (cafe) => {
  cafeToEdit.value = { ...cafe };
};

// Function to update cafe details
const updateCafeDetails = async () => {
  if (!cafeToEdit.value.name) {
    alert('Vui lòng nhập tên quán cafe cần cập nhật.');
    return;
  }
  try {
    const cafeRequest = { name: cafeToEdit.value.name };
    await updateCafeAPI(cafeToEdit.value.id, cafeRequest);
    cafeToEdit.value = {}; // Reset the edit form
    await fetchCafes(); // Reload the cafe list
  } catch (error) {
    console.error(error);
  }
};

// Function to delete a cafe
const removeCafe = async (id) => {
  if (confirm('Bạn có chắc chắn muốn xóa quán cafe này?')) {
    try {
      await deleteCafeAPI(id);
      await fetchCafes(); // Reload the cafe list
    } catch (error) {
      console.error(error);
    }
  }
};
</script>

<style scoped>
h1 {
  color: #2c3e50;
}

input {
  margin: 10px 0;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 8px 12px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

table th, table td {
  border: 1px solid #ddd;
  padding: 8px;
}

table th {
  background-color: #f2f2f2;
  text-align: left;
}

table tr:nth-child(even) {
  background-color: #f9f9f9;
}

table tr:hover {
  background-color: #f1f1f1;
}
</style>
