<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import MaterialService from '../services/MaterialService';
import Header from '@/components/Header.vue';

const route = useRoute();
const router = useRouter();
const isEditing = route.params.id !== undefined;

const form = ref({
    name: '',
    availableQuantity: 0,
    measureType: 'UNIT' // Valor padrão
});

onMounted(async () => {
    if (isEditing) {
        const resp = await MaterialService.getById(route.params.id);
        form.value = resp.data;
    }
});

const save = async () => {
    try {
        if (isEditing) {
            await MaterialService.update(route.params.id, form.value);
        } else {
            await MaterialService.create(form.value);
        }
        router.push('/materials');
    } catch (e) {
        alert('Erro ao salvar. Verifique os dados.');
    }
};
</script>

<template>
  <div class="container">
		<Header :title="`${isEditing ? 'Editar' : 'Nova'} Matéria Prima`" />
    <form @submit.prevent="save" class="mt-4 card p-3">
      <div class="mb-3">
        <label class="form-label">Nome do Material</label>
        <input v-model="form.name" type="text" class="form-control" required>
      </div>
      
      <div class="row">
        <div class="col-md-6 mb-3">
          <label class="form-label">Quantidade em Estoque</label>
          <input v-model="form.availableQuantity" type="number" step="0.01" class="form-control" required>
        </div>
        <div class="col-md-6 mb-3">
          <label class="form-label">Tipo de Medida</label>
          <select v-model="form.measureType" class="form-select">
            <option value="UNIT">Unidade</option>
            <option value="GRAMS">Gramas (G)</option>
            <option value="LITERS">Litros (L)</option>
          </select>
        </div>
      </div>

			<div class="d-flex justify-content-end gap-2">
				<button type="submit" class="btn btn-success">Salvar</button>
				<RouterLink to="/materials" class="btn btn-secondary">Cancelar</RouterLink>
			</div>
    </form>
  </div>
</template>