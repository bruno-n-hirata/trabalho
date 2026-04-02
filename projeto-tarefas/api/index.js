const express = require('express');
const cors = require('cors');
const sequelize = require('./database');
const Tarefa = require('./models/Tarefas')

const app = express();
app.use(cors());
app.use(express.json());

app.get('/tarefas', async (req, res) => {
    try {
        const tarefas = await Tarefa.findAll({
            order: [
                [sequelize.literal(`status = 'Iniciada'`), 'DESC']
            ]
        });
        res.json(tarefas);
    } catch (error) {
        res.status(400).json({ erro: 'Erro ao buscar tarefas.'});
    }
});

app.post('/tarefas', async (req, res) => {
    try {
        const { nome, descricao, tempo, status } = req.body;
        const tarefa = await Tarefa.create({ nome, descricao, tempo, status });
        res.json(tarefa);
    } catch (error) {
        res.status(400).json({ erro: 'Erro ao criar tarefa.' });
    }
});

app.delete('/tarefas/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const excluido = await Tarefa.destroy({ where: { id }});
        if (!excluido) {
            return res.status(404).json({ erro: 'Tarefa não encontrada.'});
        }
        res.json({ mensagem: 'Tarefa excluída com sucesso.'});
    } catch (error) {
        res.status(400).json({ erro: 'Erro ao excluir tarefa.' });
    }
});

app.put('/tarefas/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const { nome, descricao, tempo, status } = req.body;

        const [atualizado] = await Tarefa.update(
            { nome, descricao, tempo, status },
            { where: { id } }
        );

        if (!atualizado) {
            return res.status(404).json({ erro: 'Tarefa não encontrada.'});
        }
        res.json({ mensagem: 'Tarefa atualizada com sucesso.'});
    } catch (error) {
        res.status(400).json({ erro: 'Erro ao atualizar tarefa.' });
    }
});

const PORT = 3000;
sequelize.sync().then(() => {
    console.log('Banco de dados conectado!');
    app.listen(PORT, () => {
        console.log(`Servidor rodando em: http://localhost:${PORT}`);
    });
});
