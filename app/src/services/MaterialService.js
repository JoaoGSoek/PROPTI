import api from './api';

export default {
    getAll() { return api.get('/source-materials'); },
    getById(id) { return api.get(`/source-materials/${id}`); },
    create(data) { return api.post('/source-materials', data); },
    update(id, data) { return api.put(`/source-materials/${id}`, data); },
    delete(id) { return api.delete(`/source-materials/${id}`); }
}