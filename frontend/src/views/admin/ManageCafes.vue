<template>
  <div>
    <h1>Manager Cafes</h1>

    <!-- Nút mở modal để thêm quán cafe -->
    <el-button type="primary" @click="showAddModal">Add New Cafe</el-button>

    <h2>Các Quán Cafe</h2>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Cafe Name</th>
        <th>Address</th>
        <th>Bio</th>
        <th>Price Fluctuation</th>
        <th>Thao Tác</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="cafe in cafes" :key="cafe.cafeId">
        <td>{{ cafe.cafeId }}</td>
        <td>{{ cafe.name }}</td>
        <td>{{ cafe.address }}</td>
        <td>{{ cafe.bio }}</td>
        <td>{{ formatCurrency(cafe.priceRangeMin * 1000) }} - {{ formatCurrency(cafe.priceRangeMax * 1000) }}</td>
        <td>
          <el-button type="warning" @click="setCafeToEdit(cafe)">Sửa</el-button>
          <el-button type="danger" @click="removeCafe(cafe.cafeId)">Xóa</el-button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Dialog cho thêm quán cafe -->
    <el-dialog v-model:visible="isAddModalVisible" title="Thêm Quán Cafe">
      <div>
        <el-input v-model="newCafe.name" placeholder="Nhập tên quán cafe"></el-input>
        <el-input v-model="newCafe.address" placeholder="Nhập địa chỉ"></el-input>
        <el-input v-model="newCafe.bio" placeholder="Nhập mô tả"></el-input>

        <el-input v-model="newCafe.priceRangeMin" type="number" placeholder="Giá thấp nhất"></el-input>
        <el-input v-model="newCafe.priceRangeMax" type="number" placeholder="Giá cao nhất"></el-input>
      </div>
      <span class="dialog-footer">
        <el-button @click="isAddModalVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addCafe">Thêm</el-button>
      </span>
    </el-dialog>

    <!-- Dialog cho cập nhật quán cafe -->
    <el-dialog v-model:visible="isEditModalVisible" title="Cập Nhật Quán Cafe">
      <div>
        <el-input v-model="cafeToEdit.name" placeholder="Tên quán cafe"></el-input>
        <el-input v-model="cafeToEdit.address" placeholder="Địa chỉ"></el-input>
        <el-input v-model="cafeToEdit.bio" placeholder="Mô tả"></el-input>

        <el-input v-model="cafeToEdit.priceRangeMin" type="number" placeholder="Giá thấp nhất"></el-input>
        <el-input v-model="cafeToEdit.priceRangeMax" type="number" placeholder="Giá cao nhất"></el-input>
      </div>
      <span class="dialog-footer">
        <el-button @click="isEditModalVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateCafeDetails">Cập Nhật</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { getAllCafes, createCafe, updateCafe as updateCafeAPI, deleteCafe as deleteCafeAPI } from '@/services/admin/admin-cafe-service';

const cafes = ref([]);
const newCafe = ref({
  name: '',
  address: '',
  bio: '',
  priceRangeMin: null,
  priceRangeMax: null,
});
const cafeToEdit = ref({
  name: '',
  address: '',
  bio: '',
  priceRangeMin: null,
  priceRangeMax: null,
});

const isAddModalVisible = ref(false);
const isEditModalVisible = ref(false);

// Load cafes when component is mounted
onMounted(async () => {
  await fetchCafes();
});

// Fetch cafes
const fetchCafes = async () => {
  try {
    cafes.value = await getAllCafes();
  } catch (error) {
    console.error(error);
  }
};

// Add new cafe
const addCafe = async () => {
  if (!newCafe.value.name || !newCafe.value.address) {
    ElMessage.error('Vui lòng nhập đầy đủ thông tin.');
    return;
  }
  try {
    await createCafe(newCafe.value);
    newCafe.value = { name: '', address: '', bio: '', priceRangeMin: null, priceRangeMax: null };
    isAddModalVisible.value = false;
    await fetchCafes();
    ElMessage.success('Thêm quán cafe thành công!');
  } catch (error) {
    console.error(error);
  }
};

// Set cafe to edit
const setCafeToEdit = (cafe) => {
  cafeToEdit.value = { ...cafe };
  isEditModalVisible.value = true;
};

// Update cafe details
const updateCafeDetails = async () => {
  if (!cafeToEdit.value.name || !cafeToEdit.value.address) {
    ElMessage.error('Vui lòng nhập đầy đủ thông tin.');
    return;
  }
  try {
    await updateCafeAPI(cafeToEdit.value.cafeId, cafeToEdit.value);
    isEditModalVisible.value = false;
    await fetchCafes();
    ElMessage.success('Cập nhật quán cafe thành công!');
  } catch (error) {
    console.error(error);
  }
};

// Remove cafe
const removeCafe = async (id) => {
  if (confirm('Bạn có chắc chắn muốn xóa quán cafe này?')) {
    try {
      await deleteCafeAPI(id);
      await fetchCafes();
      ElMessage.success('Xóa quán cafe thành công!');
    } catch (error) {
      console.error(error);
    }
  }
};

// Format currency in VND
const formatCurrency = (amount) => {
  return amount.toLocaleString('vi-VN') + ' VNĐ';
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
  padding: 4px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:nth-child(1) {
  background-color: #ff4b4b;
}

button:nth-child(2) {
  background-color: #666666;
  margin-left: 10px;
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
