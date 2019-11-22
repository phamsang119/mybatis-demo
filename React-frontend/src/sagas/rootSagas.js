import { all } from "redux-saga/effects";
import {bookSaga} from './bookSaga';

export default function* rootSagas() {
    yield all ([
        ...bookSaga
    ])
}