import { instance } from '../api-instance-provider';

// Hàm lấy danh sách các gói đăng ký
export const getAllSubscriptionPlans = async () => {
  try {
    const response = await instance.get('/admin/subscriptionPlan', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('userToken')}`, // Gửi token trong header
      },
    });
    return response?.data?.data; // Trả về danh sách gói từ API
  } catch (error) {
    throw error.response ? error.response.data : { message: 'An error occurred while fetching subscriptionPlan' };
  }
};
