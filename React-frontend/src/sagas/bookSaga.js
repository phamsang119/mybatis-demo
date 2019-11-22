import { put, call, takeLatest } from "redux-saga/effects";
import { GET_BOOK_LIST, UPDATE_BOOKS, DELETE_BOOKS } from "../actions/ActionType";
import {
  getBookListSuccess,
  getBookListFailed,
  updateBooksSuccess,
  updateBooksFailed,
  deleteBooksSuccess,
  deleteBooksFailed
} from "../actions/BookAction";
import { bookService } from "../services/BookService";

export function* getBooks(action) {
  try {
    const response = yield call(
      bookService.get,
      action.payload.api,
      action.payload.param
    );
    yield put(getBookListSuccess(response));
  } catch (error) {
    yield put(getBookListFailed(error));
  }
}

export function* updateBooks(action) {
  try {
    yield call(bookService.post, action.payload.api, action.payload.data);
    alert("Saved")
    yield put(updateBooksSuccess(action.payload.data));
  } catch (error) {
    yield put(updateBooksFailed(error));
  }
}

export function* deleteBooks(action) {
    try {
        yield call(bookService.deleteByIds, action.payload.api, action.payload.data);
        alert("Deleted");
        yield put(deleteBooksSuccess(action.payload.data));
    } catch (error) {
        yield put(deleteBooksFailed(error));
    }
}

export const bookSaga = [
  takeLatest(GET_BOOK_LIST, getBooks),
  takeLatest(UPDATE_BOOKS, updateBooks),
  takeLatest(DELETE_BOOKS, deleteBooks)
];
