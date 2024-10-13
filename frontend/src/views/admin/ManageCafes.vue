<template>
  <div>
    <h1>Quản Lý Quán Cafe</h1>
    <button @click="fetchCafes">Tải danh sách quán cafe</button>

    <h2>Tạo Quán Cafe</h2>
    <input v-model="newCafeName" placeholder="Nhập tên quán cafe" />
    <button @click="addCafe">Thêm Quán Cafe</button>

    <h2>Các Quán Cafe</h2>
    <ul>
      <li v-for="cafe in cafes" :key="cafe.id">
        {{ cafe.name }}
        <button @click="setCafeToEdit(cafe)">Sửa</button>
        <button @click="removeCafe(cafe.id)">Xóa</button>
      </li>
    </ul>

    <h2>Cập Nhật Quán Cafe</h2>
    <input v-model="cafeToEdit.name" placeholder="Tên quán cafe" />
    <button @click="updateCafeDetails">Cập Nhật</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { getAllCafes, createCafe, updateCafe as updateCafeAPI, deleteCafe as deleteCafeAPI } from '@/services/admin/admin-cafe-service';

const cafes = ref([]);
const newCafeName = ref('');
const cafeToEdit = ref({});

// Hàm để lấy danh sách quán cafe
const fetchCafes = async () => {
  try {
    cafes.value = await getAllCafes();
  } catch (error) {
    console.error(error);
  }
};

// Hàm để tạo mới quán cafe
const addCafe = async () => {
  if (!newCafeName.value) {
    alert('Vui lòng nhập tên quán cafe.');
    return;
  }
  try {
    const cafeRequest = { name: newCafeName.value };
    await createCafe(cafeRequest);
    newCafeName.value = ''; // Reset input
    await fetchCafes(); // Tải lại danh sách quán cafe
  } catch (error) {
    console.error(error);
  }
};

// Hàm để thiết lập quán cafe cần chỉnh sửa
const setCafeToEdit = (cafe) => {
  cafeToEdit.value = { ...cafe }; // Sao chép thông tin quán cafe vào biến cafeToEdit
};

// Hàm để cập nhật quán cafe
const updateCafeDetails = async () => {
  if (!cafeToEdit.value.name) {
    alert('Vui lòng nhập tên quán cafe cần cập nhật.');
    return;
  }
  try {
    const cafeRequest = { name: cafeToEdit.value.name };
    await updateCafeAPI(cafeToEdit.value.id, cafeRequest);
    cafeToEdit.value = {}; // Reset biến cafeToEdit
    await fetchCafes(); // Tải lại danh sách quán cafe
  } catch (error) {
    console.error(error);
  }
};

// Hàm để xóa quán cafe
const removeCafe = async (id) => {
  if (confirm('Bạn có chắc chắn muốn xóa quán cafe này?')) {
    try {
      await deleteCafeAPI(id);
      await fetchCafes(); // Tải lại danh sách quán cafe
    } catch (error) {
      console.error(error);
    }
  }
};
</script>

<style>
/* Thêm CSS tùy chỉnh nếu cần */
</style>
