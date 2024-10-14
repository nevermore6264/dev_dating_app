<template>
  <div>
    <h1>Manager Cafes</h1>

    <!-- Nút mở modal để thêm quán cafe -->
    <el-button type="success" @click="showAddModal">Add New Cafe</el-button>

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

          <!-- Price Range Row -->
          <el-row :gutter="20">
            <!-- Price Range (Min) -->
            <el-col :span="12">
              <el-form-item label="Price Range (Min)">
                <el-input v-model="newCafe.priceRangeMin" type="number" placeholder="Minimum price">
                  <template #append>VNĐ</template>
                </el-input>
              </el-form-item>
            </el-col>

            <!-- Price Range (Max) -->
            <el-col :span="12">
              <el-form-item label="Price Range (Max)">
                <el-input v-model="newCafe.priceRangeMax" type="number" placeholder="Maximum price">
                  <template #append>VNĐ</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>

      <!-- Footer Buttons -->
      <span class="dialog-footer">
        <el-button type="success" @click="addCafe">Add</el-button>
        <el-button type="danger" @click="isAddModalVisible = false">Cancel</el-button>
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
import router from "@/router";

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

    // Check if error is due to authentication (401 Unauthorized)
    if (error.response && error.response.status === 401) {
      ElMessage.error('Session expired. Redirecting to login.');
      await router.push('/');  // Redirect to the login page
    } else {
      ElMessage.error('Failed to fetch cafes. Please try again later.');
    }
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
