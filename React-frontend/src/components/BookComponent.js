import React from "react";
import { bookService } from "../services/BookService";
import { findDOMNode } from "react-dom";
import { Button } from "@material-ui/core";
import { Editors } from "react-data-grid-addons";
import ReactDataGrid from "react-data-grid";

class BookComponent extends React.Component {
  constructor(props) {
    super(props);
    this.bookTableDom = null;
    this.state = {
      editedBooks: [],
      books: [],
      offSet: 1,
      limit: 20,
      total: 0,
      selectedIndexes: [],
      selectedIds: []
    };
  }
  componentDidMount() {
    bookService
      .get("books", { page: this.state.offSet, limit: this.state.limit })
      .then(result => {
        this.setState({
          books: result.data.books,
          total: result.data.count
        });
      });
    this.bookTableDom = findDOMNode(this).querySelector(".react-grid-Canvas");
    this.bookTableDom.addEventListener("scroll", this.scrollListener);
  }

  rowGetter = i => {
    return this.state.books[i];
  };

  onRowsSelected = rows => {
    this.setState({
      selectedIndexes: this.state.selectedIndexes.concat(
        rows.map(r => r.rowIdx)
      ),
      selectedIds: this.state.selectedIds.concat(rows.map(r => r.row.id))
    });
  };

  onRowsDeselected = rows => {
    let rowIndexes = rows.map(r => r.rowIdx);
    this.setState({
      selectedIndexes: this.state.selectedIndexes.filter(
        i => rowIndexes.indexOf(i) === -1
      )
    });
  };

  onGridRowsUpdated = ({ fromRow, toRow, updated }) => {
    const rows = this.state.books.slice();
    for (let i = fromRow; i <= toRow; i++) {
      rows[i] = { ...rows[i], ...updated };
      this.updateEditedBook(rows[i]);
    }
    this.setState({ books: rows });
  };

  updateEditedBook = book => {
    let books = this.state.editedBooks.filter(b => b.id !== book.id);
    books.push(book);
    this.setState({ editedBooks: books });
  };

  onRowDelete = () => {
    bookService
      .deleteByIds("books", { listId: this.state.selectedIds })
      .then(result => {
        if (result && result.status === 200) {
          this.setState({
            books: this.state.books.filter(
              b => this.state.selectedIds.indexOf(b.id) === -1
            ),
            selectedIds: [],
            selectedIndexes: []
          });
        }
      });
  };

  scrollListener = () => {
    if (
      this.bookTableDom.scrollHeight ===
      this.bookTableDom.scrollTop + this.bookTableDom.clientHeight
    ) {
      bookService
        .get("books", { page: this.state.offSet + 1, limit: this.state.limit })
        .then(result => {
          this.setState({
            books: [...this.state.books, ...result.data.books],
            offSet: this.state.offSet + 1,
            total: result.data.count
          });
        });
    }
  };

  saveBook = () => {
    bookService
      .post("books/updateList", { listBooks: this.state.editedBooks })
      .then(response => {
        if (response && response.status === 200) {
          alert("Save successfully");
          this.setState({ editedBooks: [] });
        } else {
          alert("Save unsuccessfully");
          throw response;
        }
      });
  };

  sortRows = (initialRows, sortColumn, sortDirection) => {
    const comparer = (a, b) => {
      if (sortDirection === "ASC") {
        return a[sortColumn] > b[sortColumn] ? 1 : -1;
      } else if (sortDirection === "DESC") {
        return a[sortColumn] < b[sortColumn] ? 1 : -1;
      }
    };
    if (sortDirection !== "NONE") {
      const books = this.state.books.slice();
      this.setState({ books: books.sort(comparer) });
    }
  };

  render() {
    const { DropDownEditor } = Editors;

    const Categories = [
      { id: "it", value: "IT" },
      { id: "cooking", value: "Cooking" },
      { id: "teen", value: "Teen" }
    ];
    const categoryTypeEditor = <DropDownEditor options={Categories} />;

    const columns = [
      { key: "id", name: "ID", sortable: true },
      { key: "bookName", name: "Book Name", editable: true, sortable: true },
      { key: "author", name: "Author", editable: true, sortable: true },
      {
        key: "description",
        name: "Description",
        editable: true,
        sortable: true
      },
      { key: "price", name: "Price", editable: true, sortable: true },
      {
        key: "category",
        name: "Category",
        editor: categoryTypeEditor,
        sortable: true
      }
    ];

    return (
      <div style={{ maxWidth: "90%", margin: "auto" }}>
        <div
          className="tableButton"
          style={{ float: "right", margin: " 15px 0 15px 0" }}
        >
          <Button
            variant="outlined"
            onClick={this.saveBook}
            style={{ marginRight: "5px" }}
          >
            Save
          </Button>
          <Button variant="outlined" onClick={this.onRowDelete}>
            Delete
          </Button>
        </div>
        <ReactDataGrid
          columns={columns}
          rowGetter={this.rowGetter}
          rowsCount={this.state.books.length}
          enableCellSelect={true}
          onGridRowsUpdated={this.onGridRowsUpdated}
          minHeight={500}
          onGridSort={(sortColumn, sortDirection) =>
            this.sortRows(this.state.books, sortColumn, sortDirection)
          }
          rowSelection={{
            showCheckbox: true,
            enableShiftSelect: true,
            onRowsSelected: this.onRowsSelected,
            onRowsDeselected: this.onRowsDeselected,
            selectBy: {
              indexes: this.state.selectedIndexes
            }
          }}
        />
        <div style={{ float: "right", marginBottom: "15px" }}>
          Showing {this.state.books.length} of {this.state.total} item
        </div>
      </div>
    );
  }
}

export default BookComponent;
