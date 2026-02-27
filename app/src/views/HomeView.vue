<script setup>
import { ref } from 'vue';
import { Bot } from 'lucide-vue-next';
import ProductService from '../services/ProductService';

const suggestion = ref(null);
const loading = ref(false);

const calculateProduction = async () => {
    loading.value = true;
    try {
        const resp = await ProductService.getSuggestion();
        suggestion.value = resp.data;
    } catch (e) {
        alert("Erro ao calcular produção. Verifique se a API está rodando.");
    } finally {
        loading.value = false;
    }
};
</script>

<template>
  <div class="container text-center d-flex align-items-center flex-column gap-3 justify-content-center h-100">
    <div class="jumbotron">
      <h1 class="display-4 text-primary fw-bold">PROPTI</h1>
      <p class="lead">
				PROPTI é um micro serviço de otimização de produção (<b>PRO</b>duct + <b>OPTI</b>mization = PROPTI)
				<br>
				Desenvolvido como parte do processo seletivo para desenvolvedor fullstack na <a href="https://www.projedata.com.br" target="_blank">Projedata</a>
				<br>
				Para usar o PROPTI, configure as matérias primas relevantes para seus produtos, e cadastre seus produtos
				<br>
				Por fim, volte para essa página, e clique no botão abaixo, e o PROPTI encontrará a melhor combinação de produtos, de forma a otimizar seus ganhos
			</p>
      
      <button @click="calculateProduction" :disabled="loading" class="btn btn-primary btn-lg mt-3">
				<div v-if="loading" class="d-flex align-items-center gap-1">
					<span class="spinner-border spinner-border-sm"></span>
					<p class="mb-0">Calculando...</p>
				</div>
				<div v-else class="d-flex align-items-center gap-1">
					<Bot size="28" />
					<p class="mb-0">Otimizar produção</p>
				</div>
      </button>
    </div>

    <!-- Resultados -->
    <div v-if="suggestion" class="mt-5 text-start w-50">
        <div class="card border-success mb-3">
            <div class="card-header bg-success text-white fs-4">Resultado da Análise</div>
            <div class="card-body">
							<ul class="list-group">
								<li
									v-for="(item, index) in suggestion.products"
									:key="index"
									class="list-group-item d-flex justify-content-between align-items-center gap-2"
								>
									<p class="mb-0">{{ item.productName }}</p>
									<span class="badge bg-success rounded-pill">{{ item.quantity }} Unidades</span>
								</li>
							</ul>
							<div class="d-flex align-items-end gap-2 mt-3 justify-content-end text-success">
								<p class="mb-0">Ganho estimado:</p>
								<p class="mb-0 fw-bold fs-4">R$ {{ suggestion.totalEstimatedValue?.toFixed(2) }}</p>
							</div>
							<div v-if="suggestion.products?.length === 0" class="alert alert-warning mt-3">
									O estoque atual é insuficiente para produzir qualquer produto.
							</div>
            </div>
        </div>
    </div>
  </div>
</template>