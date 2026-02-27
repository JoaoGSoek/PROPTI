<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Trash2, Plus } from 'lucide-vue-next';
import ProductService from '../services/ProductService';
import MaterialService from '../services/MaterialService';
import Header from '@/components/Header.vue';

const route = useRoute();
const router = useRouter();
const isEditing = route.params.id !== undefined;

const form = ref({ name: '', price: 0, composition: [] });
const availableMaterials = ref([]);
const tempIngredient = ref({ materialId: '', quantity: 0 }); // Variável temporária para adicionar na lista

onMounted(async () => {
    // 1. Carrega materiais para preencher o Select
    const materialsResp = await MaterialService.getAll();
    availableMaterials.value = materialsResp.data;

    // 2. Se for edição, carrega o produto
    if (isEditing) {
        const prodResp = await ProductService.getById(route.params.id);
        form.value = prodResp.data;
    }
});

// Adiciona ingrediente na lista local (antes de salvar)
const addIngredient = () => {
    if (!tempIngredient.value.materialId || tempIngredient.value.quantity <= 0) {
        alert("Selecione um material e uma quantidade válida.");
        return;
    }

    // Busca o objeto completo para exibir o nome na tabela (UX)
    const materialObj = availableMaterials.value.find(m => m.id == tempIngredient.value.materialId);

    form.value.composition.push({
        sourceMaterialName: materialObj.name, // Para exibir nome
        sourceMaterialId: materialObj.id, // O que o backend espera (ajuste conforme seu DTO)
        neededQuantity: tempIngredient.value.quantity,
        measureType: materialObj.measureType
    });

    // Limpa campos
    tempIngredient.value = { materialId: '', quantity: 0 };
};

const removeIngredient = (index) => {
    form.value.composition.splice(index, 1);
};

const save = async () => {
    // Prepara o DTO correto para o backend
    // ATENÇÃO: Verifique como seu DTO Java espera receber isso.
    // Geralmente espera: { name: "...", price: 10, composition: [{ sourceMaterialId: 1, neededQuantity: 10 }] }
    
    const payload = {
        ...form.value,
        composition: form.value.composition.map(c => ({
            sourceMaterialId: c.sourceMaterial ? c.sourceMaterial.id : c.sourceMaterialId, // Garante pegar o ID
            neededQuantity: c.neededQuantity
        }))
    };

    try {
        if (isEditing) await ProductService.update(route.params.id, payload);
        else await ProductService.create(payload);
        router.push('/products');
    } catch (e) {
        console.error(e);
        alert('Erro ao salvar produto.');
    }
};
</script>

<template>
  <div class="container">
    <Header :title="`${isEditing ? 'Editar' : 'Novo'} Produto`" />
    
    <form @submit.prevent="save" class="mt-4 card p-3">
        <div class="row g-3">
            <!-- Dados Básicos -->
            <div class="col-md-6">
                <label class="form-label">Nome do Produto</label>
                <input v-model="form.name" type="text" class="form-control" required>
            </div>
            <div class="col-md-6">
                <label class="form-label">Preço de Venda (R$)</label>
                <input v-model="form.price" type="number" step="0.01" class="form-control" required>
            </div>

            <div class="col-12">
                <hr class="my-4">
                <h5 class="mb-3">Receita / Composição</h5>
                
                <div class="card bg-light p-3 mb-3 border-0">
                    <div class="row align-items-end g-2">
                        <div class="col-md-5">
                            <label class="form-label">Matéria-Prima</label>
                            <select v-model="tempIngredient.materialId" class="form-select">
                                <option value="">Selecione...</option>
                                <option v-for="m in availableMaterials" :key="m.id" :value="m.id">
                                    {{ m.name }} ({{ m.measureType }})
                                </option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Quantidade Necessária</label>
                            <input v-model="tempIngredient.quantity" type="number" step="0.01" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <button type="button" @click="addIngredient" class="btn btn-secondary w-100 d-flex align-items-center justify-content-center gap-2">
                                 <Plus size="18" /> Adicionar
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Tabela de Ingredientes Adicionados -->
                <table class="table table-bordered table-hover mb-0" v-if="form.composition.length > 0">
                    <thead class="table-light">
                        <tr>
                            <th>Ingrediente</th>
                            <th style="width: 200px;">Qtd. Necessária</th>
                            <th style="width: 80px;" class="text-center">Ação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in form.composition" :key="index">
                            <td>{{ item.sourceMaterialName || 'Item ID: ' + item.sourceMaterialId }}</td>
                            <td class="text-end">{{ item.neededQuantity }}</td>
                            <td class="text-center">
                                <button type="button" @click="removeIngredient(index)" class="btn btn-outline-danger btn-sm p-1 ratio-1x1" style="width: fit-content;">
                                    <Trash2 size="16" />
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div v-else class="alert alert-info mb-0">
                    Nenhum ingrediente adicionado à receita.
                </div>
            </div>
        </div>

        <div class="d-flex justify-content-end gap-2 mt-4">
            <button type="submit" class="btn btn-success">Salvar</button>
            <RouterLink to="/products" class="btn btn-secondary">Cancelar</RouterLink>
        </div>
    </form>
  </div>
</template>