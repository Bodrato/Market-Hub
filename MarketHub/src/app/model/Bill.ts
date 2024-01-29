export interface Bill {
    id_bill: number,
    bill_date: Date,
    status: string,
    valid: boolean,
    id_account_buyer: number,
    id_product: number
}