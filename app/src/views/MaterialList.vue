<script setup>
import { ref, onMounted } from 'vue';
import { Pencil, Trash2, CirclePlus } from 'lucide-vue-next';
import MaterialService from '../services/MaterialService';
import Header from '@/components/Header.vue';

const materials = ref([]);

const loadMaterials = async () => {
    try {
        const resp = await MaterialService.getAll();
        materials.value = resp.data;
    } catch (e) { alert('Erro ao carregar materiais'); }
};

const deleteMaterial = async (id) => {
    if(!confirm("Tem certeza?")) return;
    await MaterialService.delete(id);
    loadMaterials();
};

onMounted(loadMaterials);
</script>

<template>
  <div class="container">
		<Header title="Estoque de Matérias Primas" addBtnPath="/materials/new" />
		<table class="table table-hover table-bordered mb-0">
			<thead class="table-light">
				<tr class="text-center">
					<th>Nome</th>
					<th style="width: 200px;">Qtd. Disponível</th>
					<th style="width: 64px;">Unidade</th>
					<th style="width: 64px;">Ações</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="item in materials" :key="item.id">
					<td class="px-3">{{ item.name }}</td>
					<td class="text-end px-3">{{ item.availableQuantity }}</td>
					<td class="text-center px-3"><span class="badge bg-secondary">{{ item.measureType }}</span></td>
					<td class="text-end px-3">
						<div class="d-flex gap-2 justify-content-center">
							<RouterLink :to="`/materials/${item.id}`" class="btn btn-outline-primary d-flex align-items-center justify-content-center ratio-1x1 p-1" style="width: fit-content;">
								<Pencil size="16" />
							</RouterLink>
							<button @click="deleteMaterial(item.id)" class="btn btn-outline-danger d-flex align-items-center justify-content-center ratio-1x1 p-1" style="width: fit-content;">
								<Trash2 size="16" />
							</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
  </div>
</template>