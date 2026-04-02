import React, { useEffect, useState } from 'react';
import { StyleSheet, View, Text, FlatList } from 'react-native';
import api from '../api';
import Botao from './Botao';
import ExibeStatus from './ExibeStatus';

export default function Home({ navigation }) {
    const [tarefas, setTarefas] = useState([]);

    const buscarTarefas = async () => {
        try {
            const response = await api.get('/tarefas');
            setTarefas(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        const unsubscribe = navigation.addListener('focus', () => {
            buscarTarefas();
        });
        return unsubscribe;
    }, [navigation]);

    const deletarTarefa = async (id) => {
        try {
            await api.delete(`/tarefas/${id}`);
            buscarTarefas();
        } catch (error) {
            console.log(error);
        }
    }

    const marcarConcluida = async (id) => {
        try {
            await api.put(`/tarefas/${id}`, {
                status: 'Concluída'
            });
            buscarTarefas();
        } catch (error) {
            console.log(error);
        }
    }

    const renderItem = ({ item }) => (
        <View style={styles.containerItem}>
            <View style={styles.headerItem}>
                <Text style={styles.nameItem}>{item.nome}</Text>
                <ExibeStatus status={item.status} />
            </View>
            <Text>Descrição: {item.descricao}</Text>
            <Text>Tempo (horas): {item.tempo}</Text>

            <View style={styles.buttonItem}>
                <Botao title='Editar' onPress={() => navigation.navigate('Edição', { tarefa: item })} bgColor='#FF9800' />
                <View style={styles.buttonSpaceItem} />
                <Botao title='Excluir' onPress={() => deletarTarefa(item.id)} bgColor='#F44336' />
                <View style={styles.buttonSpaceItem} />
                <Botao title='Concluir' onPress={() => marcarConcluida(item.id)} bgColor='#4CAF50' />
            </View>
        </View>
    );

    return (
        <View style={styles.container}>
            <Botao title='Cadastrar Nova Tarefa' onPress={() => navigation.navigate('Cadastro')} />
            <FlatList
              data={tarefas}
              keyExtractor={(item) => item.id.toString()}
              renderItem={renderItem}
            />
        </View>
    );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },  
  containerItem: {
    borderWidth: 1,
    borderColor: '#ccc',
    marginVertical: 8,
    padding: 10,
    borderRadius: 6,
    backgroundColor: '#fff',
  },
  headerItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  nameItem: {
    fontWeight: 'bold',
    fontSize: 16,
  },
  buttonItem: {
    flexDirection: 'row',
    marginTop: 8,
  },
  buttonSpaceItem: {
    flexDirection: 'row',
    marginTop: 8,
    justifyContent: 'space-evenly',
  },
});
