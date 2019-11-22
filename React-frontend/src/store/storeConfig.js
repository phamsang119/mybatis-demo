import { createStore , applyMiddleware } from 'redux';
import createSagaMiddleware from 'redux-saga'
import rootSagas from '../sagas/rootSagas'
import reducers from '../reducers'
const sagaMiddleware = createSagaMiddleware()

const store = createStore(reducers, applyMiddleware(sagaMiddleware));
sagaMiddleware.run(rootSagas);
export default store;