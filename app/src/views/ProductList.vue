<script setup>
import { ref, onMounted } from 'vue';
import { Pencil, Trash2 } from 'lucide-vue-next';
import ProductService from '../services/ProductService';
import Header from '@/components/Header.vue';

const products = ref([]); // Estado reativo

// Função para carregar dados
const fetchProducts = async () => {
    try {
        const response = await ProductService.getAll();
        products.value = response.data;
    } catch (error) {
        console.error("Erro ao buscar produtos:", error);
        alert("Erro ao conectar com o servidor.");
    }
};

const deleteProduct = async (id) => {
    if(!confirm("Tem certeza que deseja excluir este produto?")) return;
    try {
        await ProductService.delete(id);
        fetchProducts();
    } catch (e) {
        alert('Erro ao excluir produto.');
    }
};

// Executa ao montar o componente (equivalente ao created/mounted)
onMounted(() => {
    fetchProducts();
});
</script>

<template>
  <div class="container">
    <Header title="Lista de Produtos" addBtnPath="/products/new" />

    <table class="table table-hover table-bordered mb-0">
      <thead class="table-light">
        <tr class="text-center">
          <th>Nome</th>
          <th style="width: 150px;">Preço</th>
          <th style="width: 100px;">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td class="px-3">{{ product.name }}</td>
          <td class="text-end px-3">R$ {{ product.price }}</td>
          <td class="text-end px-3">
            <div class="d-flex gap-2 justify-content-center">
              <RouterLink :to="`/products/${product.id}`" class="btn btn-outline-primary d-flex align-items-center justify-content-center ratio-1x1 p-1" style="width: fit-content;">
                <Pencil size="16" />
              </RouterLink>
              <button @click="deleteProduct(product.id)" class="btn btn-outline-danger d-flex align-items-center justify-content-center ratio-1x1 p-1" style="width: fit-content;">
                <Trash2 size="16" />
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>