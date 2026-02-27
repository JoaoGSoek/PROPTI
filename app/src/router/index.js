import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MaterialForm from "../views/MaterialForm.vue";
import MaterialList from "../views/MaterialList.vue";
import ProductForm from "../views/ProductForm.vue";
import ProductList from "../views/ProductList.vue";

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{ path: "/", name: "home", component: HomeView },
		{ path: "/products", name: "products", component: ProductList },
		{ path: "/products/new", name: "product-create", component: ProductForm },
		{ path: "/products/:id", name: "product-edit", component: ProductForm }, // Reutiliza form para edição
		{ path: "/materials", name: "materials", component: MaterialList },
		{
			path: "/materials/new",
			name: "material-create",
			component: MaterialForm,
		},
		{ path: "/materials/:id", name: "material-edit", component: MaterialForm },
	],
});

export default router;
