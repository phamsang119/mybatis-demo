import React from "react";
import { connect } from "react-redux";
import { findDOMNode } from "react-dom";
import { Button } from "@material-ui/core";
import ReactDataGrid from "react-data-grid";

class BookComponent extends React.PureComponent {
  constructor(props) {
    super(props);
    this.bookTableDom = null;
  }
  componentDidMount() {
    this.bookTableDom = findDOMNode(this).querySelector(".react-grid-Canvas");
    this.bookTableDom.addEventListener("scroll", this.scrollListener);
  }

  scrollListener = () => {
    if (
      this.bookTableDom.scrollHeight ===
      this.bookTableDom.scrollTop + this.bookTableDom.clientHeight
    ) {
      this.props.getMoreBooks();
    }
  };

  addBook = () => {
    const newBook = {
      id: "",
      bookName: "",
      author: "",
      description: "",
      price: 0,
      category: ""
    };
    this.bookTableDom.scrollTop = 0;
    this.setState({ books: [newBook, ...this.state.books] });
  };

  render() {
    console.log(this.props);
    const {
      books,
      pagingConfig,
      saveBooks,
      onRowDelete,
      onRowsSelected,
      onGridRowsUpdated,
      onRowsDeselected,
      editedBooks,
      selectedIndexes
    } = this.props;
    const editedBooksMap = new Map(editedBooks.map(book => [book.id, book]));

    return (
      <div style={{ maxWidth: "90%", margin: "auto" }}>
        <div
          className="tableButton"
          style={{ float: "right", margin: " 15px 0 15px 0" }}
        >
          <Button
            variant="outlined"
            onClick={this.addBook}
            style={{ marginRight: "5px" }}
          >
            Add
          </Button>
          <Button
            variant="outlined"
            onClick={saveBooks}
            style={{ marginRight: "5px" }}
          >
            Save
          </Button>
          <Button variant="outlined" onClick={onRowDelete}>
            Delete
          </Button>
        </div>
        <ReactDataGrid
          columns={this.props.columns}
          rowGetter={i =>
            books[i] && editedBooksMap.has(books[i].id)
              ? editedBooksMap.get(books[i].id)
              : books[i]
          }
          rowsCount={books.length}
          enableCellSelect={true}
          onGridRowsUpdated={onGridRowsUpdated}
          minHeight={500}
          rowSelection={{
            showCheckbox: true,
            enableShiftSelect: true,
            onRowsSelected: onRowsSelected,
            onRowsDeselected: onRowsDeselected,
            selectBy: {
              indexes: selectedIndexes
            }
          }}
        />
        <div style={{ float: "right", marginBottom: "15px" }}>
          Showing {books.length} of {pagingConfig.total} item
        </div>
      </div>
    );
  }
}

export default BookComponent;
