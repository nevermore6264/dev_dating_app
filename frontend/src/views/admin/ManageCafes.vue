<template>
  <div>
    <h1>Manager Cafes</h1>

    <!-- Nút mở modal để thêm quán cafe -->
    <el-button type="primary" @click="showAddModal">Add New Cafe</el-button>

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
    <el-dialog v-model="isAddModalVisible" title="Add New Cafe">
      <div>
        <el-form label-position="top">
          <!-- Cafe Name -->
          <el-form-item label="Cafe Name">
            <el-input v-model="newCafe.name" placeholder="Enter cafe name"></el-input>
          </el-form-item>

          <!-- Address -->
          <el-form-item label="Address">
            <el-input v-model="newCafe.address" placeholder="Enter address"></el-input>
          </el-form-item>

          <!-- Bio -->
          <el-form-item label="Description">
            <el-input v-model="newCafe.bio" type="textarea" placeholder="Enter description"></el-input>
          </el-form-item>

          <!-- Price Range -->
          <el-form-item label="Price Range (Min)">
            <el-input v-model="newCafe.priceRangeMin" type="number" placeholder="Minimum price"></el-input>
          </el-form-item>

          <el-form-item label="Price Range (Max)">
            <el-input v-model="newCafe.priceRangeMax" type="number" placeholder="Maximum price"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <!-- Footer Buttons -->
      <span class="dialog-footer">
        <el-button type="primary" @click="addCafe">Add</el-button>
        <el-button @click="isAddModalVisible = false">Cancel</el-button>
      </span>
    </el-dialog>


    <!-- Dialog cho cập nhật quán cafe -->
    <el-dialog v-model="isEditModalVisible" title="Cập Nhật Quán Cafe">
      <div>
        <el-form label-position="top">
          <!-- Cafe Name -->
          <el-form-item label="Cafe Name">
            <el-input v-model="newCafe.name" placeholder="Enter cafe name"></el-input>
          </el-form-item>

          <!-- Address -->
          <el-form-item label="Address">
            <el-input v-model="newCafe.address" placeholder="Enter address"></el-input>
          </el-form-item>

          <!-- Bio -->
          <el-form-item label="Description">
            <el-input v-model="newCafe.bio" type="textarea" placeholder="Enter description"></el-input>
          </el-form-item>

          <!-- Price Range -->
          <el-form-item label="Price Range (Min)">
            <el-input v-model="newCafe.priceRangeMin" type="number" placeholder="Minimum price"></el-input>
          </el-form-item>

          <el-form-item label="Price Range (Max)">
            <el-input v-model="newCafe.priceRangeMax" type="number" placeholder="Maximum price"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button type="primary" @click="updateCafeDetails">Cập Nhật</el-button>
        <el-button @click="isEditModalVisible = false">Cancel</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {ElMessage} from 'element-plus';
import {
  createCafe,
  deleteCafe as deleteCafeAPI,
  getAllCafes,
  updateCafe as updateCafeAPI
} from '@/services/admin/admin-cafe-service';

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
    newCafe.value = {name: '', address: '', bio: '', priceRangeMin: null, priceRangeMax: null};
    isAddModalVisible.value = false;
    await fetchCafes();
    ElMessage.success('Thêm quán cafe thành công!');
  } catch (error) {
    console.error(error);
  }
};

// Set cafe to edit
const setCafeToEdit = (cafe) => {
  cafeToEdit.value = {...cafe};
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

const showAddModal = () => {
  isAddModalVisible.value = true;
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

.el-dialog {
  width: 500px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.el-dialog__body {
  padding: 20px;
  background-color: #fafafa;
}

/* Input field styling */
.el-input {
  margin-bottom: 15px;
  width: 100%;
}

.el-input input {
  padding: 10px;
  font-size: 16px;
  border-radius: 6px;
  border: 1px solid #ddd;
  transition: border-color 0.3s ease;
}

.el-input input:focus {
  border-color: #3498db;
  outline: none;
}

/* Customizing buttons in the modal footer */
.dialog-footer {
  text-align: right;
  padding: 5px 0;
  display: block;
  width: 100%;
}

.el-button {
  border-radius: 6px;
  padding: 8px 20px;
  font-size: 14px;
}

.el-button--primary {
  background-color: #3498db;
  border-color: #3498db;
}

.el-button--primary:hover {
  background-color: #2980b9;
}

.el-button--primary:focus {
  outline: none;
}

.el-button--danger {
  background-color: #e74c3c;
  border-color: #e74c3c;
}

.el-button--danger:hover {
  background-color: #c0392b;
}

/* Hover and focus effects */
.el-button:hover {
  background-color: #2980b9;
  color: white;
}

.el-dialog__header {
  background-color: #3498db;
  color: white;
  padding: 15px;
  border-bottom: 1px solid #ddd;
}

.el-dialog__title {
  font-size: 18px;
  font-weight: bold;
}

.el-button + .el-button {
  margin-left: 10px;
}

</style>
