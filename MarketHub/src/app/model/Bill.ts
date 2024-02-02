export interface Bill {
    idBill: number,
    bill_date: Date,
    status: string,
    valid: boolean,
    idAccountBuyer: number,
    idProduct: number
}