import * as actionType from "../actions/ActionType";

export const getBookList = data => ({
  type: actionType.GET_BOOK_LIST,
  payload: data
});

export const getBookListSuccess = data => ({
  type: actionType.GET_BOOK_LIST_SUCCESS,
  payload: data
});

export const getBookListFailed = error => ({
  type: actionType.GET_BOOK_LIST_FAILED,
  error
});

export const updateBooks = data => ({
  type: actionType.UPDATE_BOOKS,
  payload: data
});

export const updateBooksSuccess = data => ({
  type: actionType.UPDATE_BOOKS_SUCCESS,
  payload: data
});

export const updateBooksFailed = error => ({
  type: actionType.UPDATE_BOOKS,
  error
});

export const deleteBooks = data => ({
  type: actionType.DELETE_BOOKS,
  payload: data
});
export const deleteBooksSuccess = data => ({
  type: actionType.DELETE_BOOKS_SUCCESS,
  payload: data
});
export const deleteBooksFailed = error => ({
  type: actionType.DELETE_BOOKS_FAILED,
  error
});

