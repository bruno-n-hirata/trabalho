import React, { useState } from 'react';
import { StyleSheet, View, Alert } from 'react-native';
import api from '../api';
import Botao from './Botao';
import Campo from './Campo';

export default function Cadastro({ navigation }) {
    const [nome, setNome] = useState('');
    const [descricao, setDescricao] = useState('');
    const [tempo, setTempo] = useState('');

    const criarTarefa = async () => {
        if (!nome.trim()) {
          Alert.alert("Validação", "O campo nome é obrigatório.");
          return;
        }

        try {
            await api.post('/tarefas', {
                nome,
                descricao,
                tempo: parseInt(tempo) || 0
            });
            navigation.goBack();
        } catch (error) {
            console.log('Erro ao criar tarefa:', error.message);
            Alert.alert("Erro", "Não foi possível criar a tarefa.");
        }
    };

    return (
        <View style={styles.container}>
            <Campo label='Nome da Tarefa' value={nome} onChangeText={setNome} />
            <Campo label='Descrição' value={descricao} onChangeText={setDescricao} />
            <Campo label='Tempo (horas)' value={tempo} onChangeText={setTempo} keyboardType='numeric' />
            <Botao title='Salvar' onPress={criarTarefa} />
        </View>
    );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
});
