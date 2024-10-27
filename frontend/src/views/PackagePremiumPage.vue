<template>
  <div class="layout-container">
    <!-- Sidebar -->
    <LoveBellSidebar />

    <!-- Main content -->
    <div class="subscription-container">
      <h2 class="subscription-header">LOVE BELL PACKAGES</h2>
      <hr />
      <!-- Grid for the Subscription Packages -->
      <div class="subscription-grid">
        <!-- Headers Row -->
        <div></div>
        <div
          v-for="(plan, index) in subscriptionPlans"
          :key="index"
          class="package-header"
          :class="plan.headerClass"
        >
          {{ plan.name }}
        </div>

        <!-- Feature Rows -->
        <div class="feature-title">Thích không giới hạn</div>
        <template v-for="(plan, index) in subscriptionPlans" :key="index">
          <div class="package-feature">
            <span
              class="material-icons"
              :class="{ locked: !plan.hasLikeLimit }"
            >
              {{ plan.hasLikeLimit ? "check_circle" : "lock" }}
            </span>
          </div>
        </template>

        <div class="feature-title">Xem ai Thích bạn</div>
        <template v-for="(plan, index) in subscriptionPlans" :key="index">
          <div class="package-feature">
            <span
              class="material-icons"
              :class="{ locked: !plan.hasWatchLike }"
            >
              {{ plan.hasWatchLike ? "check_circle" : "lock" }}
            </span>
          </div>
        </template>

        <div class="feature-title">Lượt Thích ưu tiên</div>
        <template v-for="(plan, index) in subscriptionPlans" :key="index">
          <div class="package-feature">
            <span
              class="material-icons"
              :class="{ locked: !plan.hasShowPriority }"
            >
              {{ plan.hasShowPriority ? "check_circle" : "lock" }}
            </span>
          </div>
        </template>

        <div class="feature-title">Xem hồ sơ</div>
        <template v-for="(plan, index) in subscriptionPlans" :key="index">
          <div class="package-feature">
            <span
              class="material-icons"
              :class="{ locked: !plan.hasViewProfile }"
            >
              {{ plan.hasViewProfile ? "check_circle" : "lock" }}
            </span>
          </div>
        </template>

        <!-- Pricing Row -->
        <div></div>
        <template v-for="(plan, index) in subscriptionPlans" :key="index">
          <!-- Add explicit binding of classes to ensure "in-use-button" applies correctly -->
          <button
            :class="[
              'price-button',
              plan.buttonClass,
              isCurrentPlan(plan.id) ? 'in-use-button' : '',
            ]"
          >
            {{
              isCurrentPlan(plan.id)
                ? "Đang sử dụng"
                : `${formatPrice(plan.price)} đ`
            }}
          </button>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";
import { getAllSubscriptionPlans } from "@/services/package-service.js";
import { getCurrentSubscriptionPlan } from "@/services/user-subcription-service";

export default {
  data() {
    return {
      subscriptionPlans: [],
      currentSubscription: null,
    };
  },
  components: {
    LoveBellSidebar,
  },
  mounted() {
    this.fetchSubscriptionPlans();
    this.fetchCurrentSubscription();
  },
  methods: {
    async fetchSubscriptionPlans() {
      try {
        const plans = await getAllSubscriptionPlans();
        console.log("🚀 ~ fetchSubscriptionPlans ~ plans:", plans)
        this.subscriptionPlans = plans.map((plan, index) => ({
          ...plan,
          id: plan.planId, // Ensure this key matches currentSubscription's planId
          buttonClass: ["pink", "gold", "black"][index], // Classes for buttons based on index
          headerClass: ["plus-header", "gold-header", "platinum-header"][index], // Classes for headers based on index
        }));
      } catch (error) {
        console.error("Error fetching subscription plans:", error);
      }
    },
    async fetchCurrentSubscription() {
      try {
        const currentSubscription = await getCurrentSubscriptionPlan();
        this.currentSubscription = currentSubscription;
        console.log("Current subscription loaded:", this.currentSubscription); // Debugging line
      } catch (error) {
        console.error("Error fetching current subscription:", error);
      }
    },
    isCurrentPlan(planId) {
      // Convert both planId and currentSubscription.planId to numbers for comparison
      return (
        this.currentSubscription &&
        Number(this.currentSubscription.planId) === Number(planId)
      );
    },
    formatPrice(price) {
      return new Intl.NumberFormat("vi-VN").format(price);
    },
  },
};
</script>

<style scoped>
.layout-container {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  background: #f8f9fa;
  min-height: 100vh;
}

.subscription-container {
  flex-grow: 1;
  padding: 40px;
  background: #ffffff;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
  border-radius: 15px;
  margin: 20px;
}

.subscription-header {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  padding: 10px;
  color: #ff6699;
  border-radius: 15px;
  animation: fadeInDown 1s ease-in-out;
}

.subscription-grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  gap: 15px;
  justify-items: center;
  align-items: center;
  animation: fadeIn 1.2s ease-in-out;
}

.package-header {
  font-weight: bold;
  padding: 20px;
  border-radius: 20px;
  color: black;
  font-size: 18px;
  text-align: center;
  width: 80%;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.package-header:hover {
  transform: translateY(-5px) scale(1.05);
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.3);
}

.plus-header {
  background: linear-gradient(135deg, #ffccf9 0%, #ffb6c1 100%);
}

.gold-header {
  background: linear-gradient(135deg, #ffd700 0%, #fffacd 100%);
}

.platinum-header {
  background: linear-gradient(135deg, #d3d3d3 0%, #f0f0f0 100%);
}

.feature-title {
  font-weight: bold;
  font-size: 18px;
  padding: 20px;
  animation: slideInLeft 0.8s ease;
}

.package-feature {
  font-size: 18px;
}

.locked {
  color: gray !important;
}

.material-icons {
  font-size: 30px;
  color: #4caf50;
  transition: transform 0.2s ease;
}

.package-feature:hover .material-icons {
  transform: scale(1.2);
}

.locked .material-icons {
  color: gray;
}

.price-button {
  padding: 15px;
  border-radius: 25px;
  font-weight: bold;
  color: white;
  text-align: center;
  width: 60%;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  border: none;
}

.price-button:hover {
  transform: translateY(-5px) scale(1.1);
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.3);
}

.price-button:active {
  transform: translateY(0);
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
}

.in-use-button {
  background: #a0a0a0 !important; /* Ensures gray background */
  color: white !important; /* Ensures white text */
  cursor: not-allowed !important; /* Indicates it's not clickable */
  box-shadow: none !important; /* Removes shadow for flat appearance */
}

.in-use-button:hover {
  transform: none !important;
  box-shadow: none !important;
}

.pink {
  background: linear-gradient(135deg, #ff70a1, #ff4081);
}

.gold {
  background: linear-gradient(135deg, #ffd700, #ffcc00);
}

.black {
  background: linear-gradient(135deg, #2c2c2c, #3a3a3a);
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes fadeInDown {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideInLeft {
  0% {
    opacity: 0;
    transform: translateX(-30px);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
}
</style>
