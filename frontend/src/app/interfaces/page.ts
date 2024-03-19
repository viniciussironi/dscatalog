export interface Page<ProductInterface> {
    content : ProductInterface[];
    totalPages: number;
    number: number;
}